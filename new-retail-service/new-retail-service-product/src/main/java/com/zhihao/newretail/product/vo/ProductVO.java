package com.zhihao.newretail.product.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductVO {

    private Integer id;

    private Integer categoryId;

    private String title;

    private String subTitle;

    private BigDecimal price;

    private String showImage;

}
