package com.zhihao.newretail.user.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Integer id;

    private String uuid;

    private String username;

    private String password;

    private String weChat;

    private String tel;

    private Integer levelId;

    private Integer isDelete;

    private Date createTime;

    private Date updateTime;

    private UserInfo userInfo;

}
