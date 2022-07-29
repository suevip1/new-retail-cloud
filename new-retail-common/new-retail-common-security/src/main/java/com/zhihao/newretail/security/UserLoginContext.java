package com.zhihao.newretail.security;

import com.zhihao.newretail.security.vo.UserLoginVO;

public class UserLoginContext {

    private static final ThreadLocal<UserLoginVO> USER_LOGIN_INFO = new ThreadLocal<>();

    public static UserLoginVO getUserLoginInfo() {
        return USER_LOGIN_INFO.get();
    }

    public static void setUserLoginInfo(UserLoginVO userLoginVO) {
        USER_LOGIN_INFO.set(userLoginVO);
    }

    public static void clean() {
        USER_LOGIN_INFO.remove();
    }

}
