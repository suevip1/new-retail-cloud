package com.zhihao.newretail.auth.service.impl;

import com.zhihao.newretail.api.admin.dto.SysUserApiDTO;
import com.zhihao.newretail.api.admin.feign.SysUserFeignService;
import com.zhihao.newretail.api.admin.vo.SysUserApiVO;
import com.zhihao.newretail.api.user.dto.UserApiDTO;
import com.zhihao.newretail.api.user.feign.UserFeignService;
import com.zhihao.newretail.api.user.vo.UserApiVO;
import com.zhihao.newretail.auth.form.UserLoginForm;
import com.zhihao.newretail.auth.service.LoginService;
import com.zhihao.newretail.auth.service.TokenService;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.MyMD5SecretUtil;
import com.zhihao.newretail.core.util.MyUUIDUtil;
import com.zhihao.newretail.security.vo.SysUserLoginVO;
import com.zhihao.newretail.security.vo.UserLoginVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private SysUserFeignService sysUserFeignService;

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

}
