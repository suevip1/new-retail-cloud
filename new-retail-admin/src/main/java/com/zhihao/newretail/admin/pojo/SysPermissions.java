package com.zhihao.newretail.admin.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class SysPermissions {

    private Integer id;

    private String name;

    private String path;

    private Integer isDelete;

    private Date createTime;

    private Date updateTime;

}
