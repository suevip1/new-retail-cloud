package com.zhihao.newretail.api.admin.vo;

import lombok.Data;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Data
public class SysUserApiVO {

    private Integer id;

    private String username;

    private String password;

    private String name;

    private String key;

    private Integer scope;

}
