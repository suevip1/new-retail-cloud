package com.zhihao.newretail.api.rbac.dto;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public class SysUserApiDTO {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SysUserApiDTO() {
    }

    public SysUserApiDTO(String username) {
        this.username = username;
    }

}
