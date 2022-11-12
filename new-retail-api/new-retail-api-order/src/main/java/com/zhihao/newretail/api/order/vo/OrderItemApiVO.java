package com.zhihao.newretail.api.order.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemApiVO {

    private Long orderId;

    private Integer spuId;

    private Integer skuId;

    private String title;

    private String skuImage;

    private String param;

    private BigDecimal price;

    private BigDecimal totalPrice;

    private Integer num;

}
