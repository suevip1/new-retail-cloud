package com.zhihao.newretail.cart.vo;

import java.math.BigDecimal;

public class CartProductVO {

    private Integer spuId;

    private Integer skuId;

    private String title;

    private String skuImage;

    private String param;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal totalPrice;

    private Integer isSaleable;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSkuImage() {
        return skuImage;
    }

    public void setSkuImage(String skuImage) {
        this.skuImage = skuImage;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getIsSaleable() {
        return isSaleable;
    }

    public void setIsSaleable(Integer isSaleable) {
        this.isSaleable = isSaleable;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public CartProductVO() {
    }

    public CartProductVO(Integer spuId,
                         Integer skuId,
                         String title,
                         String skuImage,
                         String param,
                         BigDecimal price,
                         Integer quantity,
                         BigDecimal totalPrice,
                         Integer isSaleable,
                         Boolean selected) {
        this.spuId = spuId;
        this.skuId = skuId;
        this.title = title;
        this.skuImage = skuImage;
        this.param = param;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.isSaleable = isSaleable;
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "CartProductVO{" +
                "spuId=" + spuId +
                ", skuId=" + skuId +
                ", title='" + title + '\'' +
                ", skuImage='" + skuImage + '\'' +
                ", param='" + param + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", isSaleable=" + isSaleable +
                ", selected=" + selected +
                '}';
    }

}
