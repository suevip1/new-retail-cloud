package com.zhihao.newretail.admin.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class SysRole {

    private Integer id;

    private String name;

    private String key;

    private Integer scope;

    private Integer sort;

    private Integer isDelete;

    private Date createTime;

    private Date updateTime;

}
