package com.zhihao.newretail.api.user.vo;

import lombok.Data;

@Data
public class UserApiVO {

    private Integer id;

    private String uuid;

    private String username;

    private String password;

    private String weChat;

    private String tel;

    private Integer levelId;

    private UserInfoApiVO userInfoApiVO;

}
