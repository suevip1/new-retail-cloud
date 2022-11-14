package com.zhihao.newretail.product.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Sku {

    private Integer id;

    private Integer spuId;

    private String skuImage;

    private String param;

    private BigDecimal price;

    private Integer isSaleable;

    private Integer isDelete;

    private Date createTime;

    private Date updateTime;

}
