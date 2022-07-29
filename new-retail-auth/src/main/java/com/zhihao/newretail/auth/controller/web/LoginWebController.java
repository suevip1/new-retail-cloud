package com.zhihao.newretail.auth.controller.web;

import com.zhihao.newretail.auth.form.UserLoginForm;
import com.zhihao.newretail.auth.service.LoginService;
import com.zhihao.newretail.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginWebController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public R login(@RequestBody UserLoginForm form) {
        return loginService.login(form);
    }

}
