package com.zhihao.newretail.auth.service;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.security.vo.UserLoginVO;

public interface TokenService {

    /*
    * 获取token
    * */
    String getToken(UserLoginVO userLoginVO);

    /*
    * 验证token有效性、刷新token
    * */
    R verifierToken(String token);

}