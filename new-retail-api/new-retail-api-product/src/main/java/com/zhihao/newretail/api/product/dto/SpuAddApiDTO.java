package com.zhihao.newretail.api.product.dto;

import lombok.Data;

@Data
public class SpuAddApiDTO {

    private Integer categoryId;

    private String title;

    private String subTitle;

    private String showImage;

    private String sliderImage;

    private String detailTitle;

    private String detailPram;

    private String detailImage;

}
