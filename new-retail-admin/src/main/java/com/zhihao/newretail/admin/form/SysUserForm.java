package com.zhihao.newretail.admin.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Data
public class SysUserForm {

    @NotBlank(message = "用户名或密码不能为空")
    private String username;

    @NotBlank(message = "用户名或密码不能为空")
    private String password;

    @NotNull(message = "请选择角色")
    private Integer roleId;

}
