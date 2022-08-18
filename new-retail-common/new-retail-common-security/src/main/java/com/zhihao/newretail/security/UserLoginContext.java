package com.zhihao.newretail.security;

import com.zhihao.newretail.security.vo.SysUserLoginVO;
import com.zhihao.newretail.security.vo.UserLoginVO;

public class UserLoginContext {

    private static final ThreadLocal<UserLoginVO> USER_LOGIN_INFO = new ThreadLocal<>();

    private static final ThreadLocal<SysUserLoginVO> SYS_USER_LOGIN_INFO = new ThreadLocal<>();

    public static UserLoginVO getUserLoginInfo() {
        return USER_LOGIN_INFO.get();
    }

    public static void setUserLoginInfo(UserLoginVO userLoginVO) {
        USER_LOGIN_INFO.set(userLoginVO);
    }

    public static void clean() {
        USER_LOGIN_INFO.remove();
    }

    public static SysUserLoginVO getSysUserLoginVO() {
        return SYS_USER_LOGIN_INFO.get();
    }

    public static void setSysUserLoginVO(SysUserLoginVO sysUserLoginVO) {
        SYS_USER_LOGIN_INFO.set(sysUserLoginVO);
    }

    public static void sysClean() {
        SYS_USER_LOGIN_INFO.remove();
    }

}
