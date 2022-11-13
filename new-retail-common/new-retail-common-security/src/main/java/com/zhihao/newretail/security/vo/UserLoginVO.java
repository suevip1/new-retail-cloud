package com.zhihao.newretail.security.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginVO implements Serializable {

    private Integer userId;

    private String uuid;

    private String nickName;

    private String photo;

    private String gender;

    private Integer integral;

}
