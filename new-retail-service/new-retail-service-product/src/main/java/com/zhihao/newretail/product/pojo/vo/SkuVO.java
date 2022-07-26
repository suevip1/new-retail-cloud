package com.zhihao.newretail.product.pojo.vo;

import java.math.BigDecimal;

public class SkuVO {

    private Integer id;

    private Integer spuId;

    private String param;

    private BigDecimal price;

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
        return "SkuVO{" +
                "id=" + id +
                ", spuId=" + spuId +
                ", param='" + param + '\'' +
                ", price=" + price +
                '}';
    }

}
