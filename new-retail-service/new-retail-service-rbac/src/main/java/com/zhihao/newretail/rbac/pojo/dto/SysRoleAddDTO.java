package com.zhihao.newretail.rbac.pojo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public class SysRoleAddDTO {

    @NotBlank(message = "角色名不能为空")
    private String name;

    @NotBlank(message = "权限标识不能为空")
    private String key;

    @NotNull(message = "权限范围不能为空")
    private Integer scope;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

}
