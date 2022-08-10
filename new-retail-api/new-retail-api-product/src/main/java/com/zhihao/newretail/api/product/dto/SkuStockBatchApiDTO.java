package com.zhihao.newretail.api.product.dto;

import java.util.Set;

public class SkuStockBatchApiDTO extends SkuBatchApiDTO {

    public SkuStockBatchApiDTO() {
    }

    public SkuStockBatchApiDTO(Set<Integer> idSet) {
        super(idSet);
    }

}
