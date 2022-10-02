package com.zhihao.newretail.auth.service;

import com.alipay.api.AlipayApiException;
import com.zhihao.newretail.auth.form.UserLoginForm;

public interface LoginService {

    /*
    * 用户登录(账号密码)
    * */
    String login(UserLoginForm form);

    /*
     * 使用支付宝PC网页登录
     * */
    String aliPayPCLogin(String code) throws AlipayApiException;

    /*
    * 后台管理系统用户登录
    * */
    String loginAdmin(UserLoginForm form);

}
