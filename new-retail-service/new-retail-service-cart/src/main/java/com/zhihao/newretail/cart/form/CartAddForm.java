package com.zhihao.newretail.cart.form;

import javax.validation.constraints.NotNull;

public class CartAddForm {

    @NotNull(message = "商品不能为空")
    private Integer spuId;

    @NotNull(message = "商品规格不能为空")
    private Integer skuId;

    @NotNull(message = "数量不能为空")
    private Integer quantity;

    private Boolean selected = true;

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

    @Override
    public String toString() {
        return "CartAddForm{" +
                "spuId=" + spuId +
                ", skuId=" + skuId +
                ", quantity=" + quantity +
                ", selected=" + selected +
                '}';
    }

}
