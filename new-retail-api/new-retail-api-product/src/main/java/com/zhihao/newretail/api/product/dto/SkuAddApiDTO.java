package com.zhihao.newretail.api.product.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SkuAddApiDTO {

    private Integer spuId;

    private String skuImage;

    private String param;

    private BigDecimal price;

    private Integer stock;

}
