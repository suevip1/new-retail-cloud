package com.zhihao.newretail.api.admin.dto;

import lombok.Data;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Data
public class SysUserApiDTO {

    private String username;

    public SysUserApiDTO(String username) {
        this.username = username;
    }

}
