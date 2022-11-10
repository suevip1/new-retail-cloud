package com.zhihao.newretail.api.user.vo;

import lombok.Data;

@Data
public class UserAddressApiVO {

    private Integer id;

    private String name;

    private String tel;

    private String address;

    private String addressDetail;

    private Integer isPrime;

}
