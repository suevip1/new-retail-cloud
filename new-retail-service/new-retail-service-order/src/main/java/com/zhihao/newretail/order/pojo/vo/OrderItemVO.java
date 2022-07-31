package com.zhihao.newretail.order.pojo.vo;

import java.math.BigDecimal;

public class OrderItemVO {

    private Long orderId;

    private Integer spuId;

    private Integer skuId;

    private String title;

    private String skuImage;

    private String param;

    private BigDecimal price;

    private BigDecimal totalPrice;

    private Integer num;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "OrderItemVO{" +
                "orderId=" + orderId +
                ", spuId=" + spuId +
                ", skuId=" + skuId +
                ", title='" + title + '\'' +
                ", skuImage='" + skuImage + '\'' +
                ", param='" + param + '\'' +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", num=" + num +
                '}';
    }

}
