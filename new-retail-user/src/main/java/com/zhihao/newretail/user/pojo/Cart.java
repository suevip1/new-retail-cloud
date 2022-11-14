package com.zhihao.newretail.user.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Cart implements Serializable {

    private Integer spuId;

    private Integer skuId;

    private Integer quantity;

    private Boolean selected;

    public Cart(Integer spuId, Integer skuId, Integer quantity, Boolean selected) {
        this.spuId = spuId;
        this.skuId = skuId;
        this.quantity = quantity;
        this.selected = selected;
    }

}
