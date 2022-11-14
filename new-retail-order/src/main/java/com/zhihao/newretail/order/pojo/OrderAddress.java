package com.zhihao.newretail.order.pojo;

import lombok.Data;

@Data
public class OrderAddress {

    private Long orderId;

    private String name;

    private String tel;

    private String address;

    private String addressDetail;

    private Integer orderAddressSharding;

}
