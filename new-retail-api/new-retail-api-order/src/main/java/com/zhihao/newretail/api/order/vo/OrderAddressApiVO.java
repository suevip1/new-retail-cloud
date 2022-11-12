package com.zhihao.newretail.api.order.vo;

import lombok.Data;

@Data
public class OrderAddressApiVO {

    private Long orderId;

    private String name;

    private String tel;

    private String address;

    private String addressDetail;

}
