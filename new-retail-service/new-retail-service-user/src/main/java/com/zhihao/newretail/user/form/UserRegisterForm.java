package com.zhihao.newretail.user.form;

import javax.validation.constraints.NotBlank;

public class UserRegisterForm {

    @NotBlank(message = "用户名或密码不能为空")
    private String username;

    @NotBlank(message = "用户名或密码不能为空")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
