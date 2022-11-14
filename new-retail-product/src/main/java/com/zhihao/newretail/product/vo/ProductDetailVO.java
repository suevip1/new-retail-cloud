package com.zhihao.newretail.product.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProductDetailVO {

    private Integer id;

    private Integer categoryId;

    private String title;

    private String subTitle;

    private ProductInfoVO productInfoVO;

    private List<GoodsVO> goodsVOList;

}
