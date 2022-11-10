package com.zhihao.newretail.user.pojo;

import lombok.Data;

@Data
public class UserAddress {

    private Integer id;

    private Integer userId;

    private String name;

    private String tel;

    private String address;

    private String addressDetail;

    private Integer isPrime;

}
