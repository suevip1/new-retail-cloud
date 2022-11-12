package com.zhihao.newretail.api.product.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProductApiVO {

    private Integer id;

    private Integer categoryId;

    private String title;

    private String subTitle;

    private Integer isSaleable;

    private String showImage;

    private List<GoodsApiVO> goodsApiVOList;

}
