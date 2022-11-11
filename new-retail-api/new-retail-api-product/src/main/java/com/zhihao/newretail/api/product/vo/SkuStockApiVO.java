package com.zhihao.newretail.api.product.vo;

import lombok.Data;

@Data
public class SkuStockApiVO {

    private Integer id;

    private Integer skuId;

    private Integer actualStock;

    private Integer lockStock;

    private Integer stock;

}
