package com.zhihao.newretail.api.product.vo;

import java.math.BigDecimal;

public class GoodsApiVO {

    private Integer id;

    private Integer spuId;

    private String title;

    private String skuImage;

    private String param;

    private BigDecimal price;

    private Integer isSaleable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpuId() {
        return spuId;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
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

    public Integer getIsSaleable() {
        return isSaleable;
    }

    public void setIsSaleable(Integer isSaleable) {
        this.isSaleable = isSaleable;
    }

    @Override
    public String toString() {
        return "GoodsApiVO{" +
                "id=" + id +
                ", spuId=" + spuId +
                ", title='" + title + '\'' +
                ", skuImage='" + skuImage + '\'' +
                ", param='" + param + '\'' +
                ", price=" + price +
                ", isSaleable=" + isSaleable +
                '}';
    }

}
