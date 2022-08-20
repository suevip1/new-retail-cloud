package com.zhihao.newretail.api.product.dto;

import java.math.BigDecimal;

public class SkuAddApiDTO {

    private Integer spuId;

    private String skuImage;

    private String param;

    private BigDecimal price;

    public Integer getSpuId() {
        return spuId;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
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

    @Override
    public String toString() {
        return "SkuAddApiDTO{" +
                "spuId=" + spuId +
                ", skuImage='" + skuImage + '\'' +
                ", param='" + param + '\'' +
                ", price=" + price +
                '}';
    }

}
