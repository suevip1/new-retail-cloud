package com.zhihao.newretail.api.product.dto;

import lombok.Data;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Data
public class SkuStockLockApiDTO {

    private Integer spuId;

    private Integer skuId;

    private Long orderId;

    private Integer count;

    private Integer status;

    private Integer mqVersion;

}
