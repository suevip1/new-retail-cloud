package com.zhihao.newretail.product.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class SkuStock {

    private Integer id;

    private Integer skuId;

    private Integer actualStock;

    private Integer lockStock;

    private Integer stock;

    private Integer isDelete;

    private Date createTime;

    private Date updateTime;

}
