package com.zhihao.newretail.user.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegisterForm {

    @NotBlank(message = "用户名或密码不能为空")
    private String username;

    @NotBlank(message = "用户名或密码不能为空")
    private String password;

}
