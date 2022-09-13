package com.zhihao.newretail.rbac.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public class SysUserForm {

    @NotBlank(message = "用户名或密码不能为空")
    private String username;

    @NotBlank(message = "用户名或密码不能为空")
    private String password;

    @NotNull(message = "请选择角色")
    private Integer roleId;

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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}
