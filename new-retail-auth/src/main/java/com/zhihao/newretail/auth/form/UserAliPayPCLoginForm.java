package com.zhihao.newretail.auth.form;

import javax.validation.constraints.NotBlank;

public class UserAliPayPCLoginForm {

    @NotBlank(message = "临时授权码不能为空")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
