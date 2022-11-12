package com.zhihao.newretail.product.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsVO {

    private Integer id;

    private String skuImage;

    private String param;

    private BigDecimal price;

}
