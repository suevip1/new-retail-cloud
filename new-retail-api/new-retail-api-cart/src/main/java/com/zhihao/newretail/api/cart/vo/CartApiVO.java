package com.zhihao.newretail.api.cart.vo;

public class CartApiVO {

    private Integer spuId;

    private Integer skuId;

    private Integer quantity;

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

    @Override
    public String toString() {
        return "CartApiVO{" +
                "spuId=" + spuId +
                ", skuId=" + skuId +
                ", quantity=" + quantity +
                '}';
    }

}
