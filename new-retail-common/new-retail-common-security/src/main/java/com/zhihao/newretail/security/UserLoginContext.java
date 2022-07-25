package com.zhihao.newretail.security;

public class UserLoginContext {

    private static final ThreadLocal<Integer> USER_LOGIN_INFO = new ThreadLocal<>();

    public static Integer getUserLoginInfo() {
        return USER_LOGIN_INFO.get();
    }

    public static void setUserLoginInfo(Integer userLoginInfo) {
        USER_LOGIN_INFO.set(userLoginInfo);
    }

    public static void clean() {
        USER_LOGIN_INFO.remove();
    }

}
