package com.zhihao.newretail.auth.service.impl;

import com.zhihao.newretail.api.user.dto.UserApiDTO;
import com.zhihao.newretail.api.user.feign.UserFeignService;
import com.zhihao.newretail.api.user.vo.UserApiVO;
import com.zhihao.newretail.auth.form.UserLoginForm;
import com.zhihao.newretail.auth.service.LoginService;
import com.zhihao.newretail.auth.service.TokenService;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.MyMD5SecretUtil;
import com.zhihao.newretail.security.vo.UserLoginVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserFeignService userFeignService;

    @Override
    public String login(UserLoginForm form) {
        String username = form.getUsername();
        String password = form.getPassword();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
            throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "用户名或密码不能为空");

        UserApiDTO userApiDTO = new UserApiDTO();
        userApiDTO.setUsername(username);
        UserApiVO userInfo = userFeignService.getUserInfo(userApiDTO);

        if (!ObjectUtils.isEmpty(userInfo.getId())) {
            String secretPassword = MyMD5SecretUtil.getSecretPassword(password, userInfo.getUuid());
            if (!secretPassword.equals(userInfo.getPassword())) {
                throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "密码错误");
            }
            UserLoginVO userLoginVO = new UserLoginVO();
            userLoginVO.setUserId(userInfo.getId());
            userLoginVO.setUuid(userInfo.getUuid());
            userLoginVO.setNickName(userInfo.getUserInfoApiVO().getNickName());
            userLoginVO.setPhoto(userInfo.getUserInfoApiVO().getPhoto());
            return tokenService.getToken(userLoginVO);
        }
        throw new ServiceException(HttpStatus.SC_NOT_FOUND, "用户不存在");
    }

}
