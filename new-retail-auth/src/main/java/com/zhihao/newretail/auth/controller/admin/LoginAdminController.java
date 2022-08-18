package com.zhihao.newretail.auth.controller.admin;

import com.zhihao.newretail.auth.form.UserLoginForm;
import com.zhihao.newretail.auth.service.LoginService;
import com.zhihao.newretail.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class LoginAdminController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public R login(@Valid @RequestBody UserLoginForm form) {
        return loginService.loginAdmin(form);
    }

}
