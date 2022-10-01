package com.zhihao.newretail.auth.service;

import com.zhihao.newretail.auth.form.UserLoginForm;

public interface LoginService {

    /*
    * 用户登录(账号密码)
    * */
    String login(UserLoginForm form);

    /*
    * 后台管理系统用户登录
    * */
    String loginAdmin(UserLoginForm form);

}
