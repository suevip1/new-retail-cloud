package com.zhihao.newretail.admin.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class SysUser {

    private Integer id;

    private String username;

    private String password;

    private Integer isDelete;

    private Date createTime;

    private Date updateTime;

    private SysRole sysRole;

}
