package com.zhihao.newretail.auth.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zhihao.newretail.api.admin.dto.SysUserApiDTO;
import com.zhihao.newretail.api.admin.feign.SysUserFeignService;
import com.zhihao.newretail.api.admin.vo.SysUserApiVO;
import com.zhihao.newretail.api.user.dto.UserApiDTO;
import com.zhihao.newretail.api.user.feign.UserFeignService;
import com.zhihao.newretail.api.user.vo.UserApiVO;
import com.zhihao.newretail.auth.config.AuthAliPayParamConfig;
import com.zhihao.newretail.auth.form.UserLoginForm;
import com.zhihao.newretail.auth.service.LoginService;
import com.zhihao.newretail.auth.service.TokenService;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.MyMD5SecretUtil;
import com.zhihao.newretail.core.util.MyUUIDUtil;
import com.zhihao.newretail.security.vo.SysUserLoginVO;
import com.zhihao.newretail.security.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private SysUserFeignService sysUserFeignService;

    @Autowired
    private AuthAliPayParamConfig authAliPayParamConfig;

    private static final Gson gson = new GsonBuilder().create();

    @Override
    public String login(UserLoginForm form) {
        String username = form.getUsername();
        String password = form.getPassword();

        UserApiDTO userApiDTO = new UserApiDTO();
        userApiDTO.setUsername(username);
        UserApiVO userInfo = userFeignService.getUserApiVO(userApiDTO);
        if (ObjectUtils.isEmpty(userInfo)) {
            throw new ServiceException("用户服务繁忙");
        } else if (ObjectUtils.isEmpty(userInfo.getId())) {
            throw new ServiceException("用户不存在");
        } else if (!userInfo.getPassword().equals(MyMD5SecretUtil.getSecretPassword(password, userInfo.getUuid()))) {
            throw new ServiceException("密码错误");
        } else {
            UserLoginVO userLoginVO = new UserLoginVO();
            userLoginVO.setUserId(userInfo.getId());
            userLoginVO.setUuid(userInfo.getUuid());
            userLoginVO.setNickName(userInfo.getUserInfoApiVO().getNickName());
            userLoginVO.setPhoto(userInfo.getUserInfoApiVO().getPhoto());

            String token = tokenService.getToken(userLoginVO);
            if (!StringUtils.isEmpty(token)) {
                return token;
            }
            throw new ServiceException("登录失败");
        }
    }

    @Override
    public String aliPayPCLogin(String code) throws AlipayApiException {
        AlipaySystemOauthTokenResponse oauthTokenResponse = oauthToken(code);
        AlipayUserInfoShareResponse userInfoShareResponse = userInfoShare(oauthTokenResponse.getAccessToken());
        String body = userInfoShareResponse.getBody();
        UserApiVO userApiVO = userFeignService.getUserApiVO(alipayUserInfoShareResponse2UserApiDTO(body));
        UserLoginVO userLoginVO = userApiVOBuildUserLoginVO(userApiVO);
        String token = tokenService.getToken(userLoginVO);
        if (!StringUtils.isEmpty(token)) {
            return token;
        }
        throw new ServiceException("登录失败");
    }

    @Override
    public String loginAdmin(UserLoginForm form) {
        SysUserApiVO sysUserApiVO = sysUserFeignService.getSysUserApiVO(new SysUserApiDTO(form.getUsername()));
        if (ObjectUtils.isEmpty(sysUserApiVO)) {
            throw new ServiceException("系统服务繁忙");
        }
        String username = sysUserApiVO.getUsername();
        String password = form.getPassword();
        String secretPassword = MyMD5SecretUtil.getSecretPassword(password, username);

        if (ObjectUtils.isEmpty(sysUserApiVO.getId())) {
            throw new ServiceException("用户不存在");
        } else if (!secretPassword.equals(sysUserApiVO.getPassword())) {
            throw new ServiceException("密码错误");
        } else {
            SysUserLoginVO sysUserLoginVO = new SysUserLoginVO();
            sysUserLoginVO.setUserToken(MyUUIDUtil.getUUID());
            sysUserLoginVO.setId(sysUserApiVO.getId());
            sysUserLoginVO.setUsername(sysUserApiVO.getUsername());
            sysUserLoginVO.setName(sysUserApiVO.getName());

            String token = tokenService.getToken(sysUserLoginVO);
            if (!StringUtils.isEmpty(token)) {
                return token;
            }
            throw new ServiceException("登录失败");
        }
    }

    private AlipaySystemOauthTokenResponse oauthToken(String code) throws AlipayApiException {
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(code);
        request.setGrantType("authorization_code");
        return authAliPayParamConfig.alipayClient().execute(request);
    }

    private AlipayUserInfoShareResponse userInfoShare(String authToken) throws AlipayApiException {
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
        return authAliPayParamConfig.alipayClient().execute(request, authToken);
    }

    private UserApiDTO alipayUserInfoShareResponse2UserApiDTO(String body) {
        JsonObject json = gson.fromJson(body, JsonObject.class);
        JsonObject jsonObject = json.getAsJsonObject("alipay_user_info_share_response");
        if (!ObjectUtils.isEmpty(jsonObject.get("user_id"))) {
            UserApiDTO userApiDTO = new UserApiDTO();
            userApiDTO.setWeChat(jsonObject.get("user_id").getAsString());
            userApiDTO.setNickName(jsonObject.get("nick_name").getAsString());
            userApiDTO.setPhoto(jsonObject.get("avatar").getAsString());
            return userApiDTO;
        } else {
            throw new ServiceException("临时授权已过期");
        }
    }

    private UserLoginVO userApiVOBuildUserLoginVO(UserApiVO userApiVO) {
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setUserId(userApiVO.getId());
        userLoginVO.setUuid(userApiVO.getUuid());
        userLoginVO.setNickName(userApiVO.getUserInfoApiVO().getNickName());
        userLoginVO.setPhoto(userApiVO.getUserInfoApiVO().getPhoto());
        return userLoginVO;
    }

}
