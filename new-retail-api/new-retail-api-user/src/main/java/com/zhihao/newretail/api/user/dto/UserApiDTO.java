package com.zhihao.newretail.api.user.dto;

import lombok.Data;

@Data
public class UserApiDTO {

    private Integer id;

    private String uuid;

    private String username;

    private String weChat;

    private String tel;

    private String nickName;

    private String photo;

}
