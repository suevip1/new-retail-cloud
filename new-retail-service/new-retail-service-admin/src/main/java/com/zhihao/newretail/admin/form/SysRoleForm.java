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
public class SysRoleForm {

    @NotBlank(message = "角色名不能为空")
    private String name;

    @NotBlank(message = "权限标识不能为空")
    private String key;

    @NotNull(message = "权限范围不能为空")
    private Integer scope;

}
