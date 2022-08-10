package com.zhihao.newretail.api.product.dto;

import java.util.List;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public class SkuStockLockBatchApiDTO {

    private List<SkuStockLockApiDTO> skuStockLockApiDTOList;

    public SkuStockLockBatchApiDTO() {
    }

    public SkuStockLockBatchApiDTO(List<SkuStockLockApiDTO> skuStockLockApiDTOList) {
        this.skuStockLockApiDTOList = skuStockLockApiDTOList;
    }

    public List<SkuStockLockApiDTO> getSkuStockLockApiDTOList() {
        return skuStockLockApiDTOList;
    }

    public void setSkuStockLockApiDTOList(List<SkuStockLockApiDTO> skuStockLockApiDTOList) {
        this.skuStockLockApiDTOList = skuStockLockApiDTOList;
    }

}
