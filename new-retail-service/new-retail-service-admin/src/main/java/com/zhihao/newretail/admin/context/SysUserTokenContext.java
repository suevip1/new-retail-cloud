package com.zhihao.newretail.admin.context;

public class SysUserTokenContext {

    private static final ThreadLocal<String> USER_TOKEN_CONTEXT = new ThreadLocal<>();

    public static String getUserToken() {
        return USER_TOKEN_CONTEXT.get();
    }

    public static void setUserToken(String userToken) {
        USER_TOKEN_CONTEXT.set(userToken);
    }

    public static void clean() {
        USER_TOKEN_CONTEXT.remove();
    }

}
