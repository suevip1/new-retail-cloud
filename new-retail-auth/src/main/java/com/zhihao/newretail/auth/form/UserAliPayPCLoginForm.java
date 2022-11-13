package com.zhihao.newretail.auth.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserAliPayPCLoginForm {

    @NotBlank(message = "临时授权码不能为空")
    private String code;

}
