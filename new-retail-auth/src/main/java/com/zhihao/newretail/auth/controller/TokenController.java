package com.zhihao.newretail.auth.controller;

import com.zhihao.newretail.auth.service.TokenService;
import com.zhihao.newretail.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/verifierToken")
    public R verifierToken(@RequestHeader("token") String token) {
        return tokenService.verifierToken(token);
    }

}
