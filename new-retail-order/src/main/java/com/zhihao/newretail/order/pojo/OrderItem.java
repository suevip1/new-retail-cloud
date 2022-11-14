package com.zhihao.newretail.order.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {

    private Long orderId;

    private Integer skuId;

    private BigDecimal price;

    private BigDecimal totalPrice;

    private Integer num;

    private Integer orderItemSharding;

}
