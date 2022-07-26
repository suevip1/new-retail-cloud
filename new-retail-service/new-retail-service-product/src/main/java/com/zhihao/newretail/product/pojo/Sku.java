package com.zhihao.newretail.product.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Sku {

    private Integer id;

    private Integer spuId;

    private String skuImage;

    private String param;

    private BigDecimal price;

    private Integer isSaleable;

    private Integer isDelete;

    private Date createTime;

    private Date updateTime;

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

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Sku{" +
                "id=" + id +
                ", spuId=" + spuId +
                ", skuImage='" + skuImage + '\'' +
                ", param='" + param + '\'' +
                ", price=" + price +
                ", isSaleable=" + isSaleable +
                ", isDelete=" + isDelete +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
