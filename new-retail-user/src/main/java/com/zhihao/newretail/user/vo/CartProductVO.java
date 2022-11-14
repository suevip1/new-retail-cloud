package com.zhihao.newretail.user.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartProductVO {

    private Integer spuId;

    private Integer skuId;

    private String title;

    private String skuImage;

    private String param;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal totalPrice;

    private Integer isSaleable;

    private Boolean selected;

}
