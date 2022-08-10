package com.zhihao.newretail.api.product.dto;

import java.util.Set;

public class SkuBatchApiDTO {

    private Set<Integer> idSet;

    public SkuBatchApiDTO() {
    }

    public SkuBatchApiDTO(Set<Integer> idSet) {
        this.idSet = idSet;
    }

    public Set<Integer> getIdSet() {
        return idSet;
    }

    public void setIdSet(Set<Integer> idSet) {
        this.idSet = idSet;
    }

}
