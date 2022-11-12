package com.zhihao.newretail.api.product.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsApiVO {

    private Integer id;

    private Integer spuId;

    private String title;

    private String skuImage;

    private String param;

    private BigDecimal price;

    private Integer isSaleable;

}
