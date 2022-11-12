package com.zhihao.newretail.api.product.vo;

import lombok.Data;

@Data
public class SpuApiVO {

    private Integer id;

    private Integer categoryId;

    private String title;

    private String subTitle;

    private SpuInfoApiVO spuInfoApiVO;

}
