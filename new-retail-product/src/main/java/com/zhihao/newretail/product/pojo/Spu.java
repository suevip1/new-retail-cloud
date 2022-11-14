package com.zhihao.newretail.product.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Spu {

    private Integer id;

    private Integer categoryId;

    private String title;

    private String subTitle;

    private Integer isSaleable;

    private Integer isDelete;

    private Date createTime;

    private Date updateTime;

    private SpuInfo spuInfo;

    private List<Sku> skuList;

}
