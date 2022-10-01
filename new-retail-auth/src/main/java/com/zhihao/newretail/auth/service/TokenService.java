package com.zhihao.newretail.auth.service;

import com.zhihao.newretail.auth.enums.TokenTypeEnum;
import com.zhihao.newretail.security.vo.SysUserLoginVO;
import com.zhihao.newretail.security.vo.UserLoginVO;

public interface TokenService {

    /*
    * 获取token
    * */
    String getToken(UserLoginVO userLoginVO);

    /*
    * 获取token(后台系统用户)
    * */
    String getToken(SysUserLoginVO sysUserLoginVO);

    /*
    * 验证token有效性、刷新token
    * */
    String verifierToken(String token, TokenTypeEnum tokenTypeEnum);

}
