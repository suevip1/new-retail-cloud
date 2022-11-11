package com.zhihao.newretail.product.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class SkuStockLock {

    private Long orderId;

    private Integer skuId;

    private Integer spuId;

    private Integer count;

    private Integer status;

    private Integer mqVersion;

    private Date createTime;

    private Date updateTime;

}
