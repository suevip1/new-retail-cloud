package com.zhihao.newretail.cart.pojo;

import java.io.Serializable;

public class Cart implements Serializable {

    private Integer spuId;

    private Integer skuId;

    private Integer quantity;

    private Boolean selected;

    public Integer getSpuId() {
        return spuId;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Cart() {
    }

    public Cart(Integer spuId, Integer skuId, Integer quantity, Boolean selected) {
        this.spuId = spuId;
        this.skuId = skuId;
        this.quantity = quantity;
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "spuId=" + spuId +
                ", skuId=" + skuId +
                ", quantity=" + quantity +
                ", selected=" + selected +
                '}';
    }

}
