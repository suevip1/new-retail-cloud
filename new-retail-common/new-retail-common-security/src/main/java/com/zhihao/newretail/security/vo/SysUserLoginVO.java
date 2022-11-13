package com.zhihao.newretail.security.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserLoginVO implements Serializable {

    private String userToken;

    private Integer id;

    private String username;

    private String name;

}
