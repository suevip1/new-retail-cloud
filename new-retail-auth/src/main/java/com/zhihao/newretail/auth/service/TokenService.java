package com.zhihao.newretail.auth.service;

import com.zhihao.newretail.security.vo.UserLoginVO;

public interface TokenService {

    /*
    * 获取token
    * */
    String getToken(UserLoginVO userLoginVO);

}
