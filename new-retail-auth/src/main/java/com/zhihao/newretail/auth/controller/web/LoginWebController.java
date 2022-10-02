package com.zhihao.newretail.auth.controller.web;

import com.alipay.api.AlipayApiException;
import com.zhihao.newretail.auth.form.UserAliPayPCLoginForm;
import com.zhihao.newretail.auth.form.UserLoginForm;
import com.zhihao.newretail.auth.service.LoginService;
import com.zhihao.newretail.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class LoginWebController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public R login(@Valid @RequestBody UserLoginForm form) {
        String token = loginService.login(form);
        return R.ok().put("token", token);
    }

    @PostMapping("/login/alipay")
    public R aliPayPCLogin(@Valid @RequestBody UserAliPayPCLoginForm form) throws AlipayApiException {
        String token = loginService.aliPayPCLogin(form.getCode());
        return R.ok().put("token", token);
    }

}
