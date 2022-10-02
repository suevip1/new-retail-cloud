package com.zhihao.newretail.auth.controller;

import com.zhihao.newretail.auth.enums.TokenTypeEnum;
import com.zhihao.newretail.auth.service.TokenService;
import com.zhihao.newretail.core.util.R;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/verifierToken")
    public R verifierToken(@RequestHeader("token") String token,
                           @RequestParam(value = "tokenType", required = false) TokenTypeEnum tokenTypeEnum) {
        String newToken = tokenService.verifierToken(token, tokenTypeEnum);
        if (!StringUtils.isEmpty(newToken)) {
            return R.ok().put("token", newToken);
        } else {
            return R.error(HttpStatus.SC_UNAUTHORIZED, "凭证已过期，请重新登录").put("token", newToken);
        }
    }

}
