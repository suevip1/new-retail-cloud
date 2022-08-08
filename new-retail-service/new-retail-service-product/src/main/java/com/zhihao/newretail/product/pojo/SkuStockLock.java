package com.zhihao.newretail.product.pojo;

import java.util.Date;

public class SkuStockLock {

    private Long orderId;

    private Integer skuId;

    private Integer spuId;

    private Integer count;

    private Integer status;

    private Integer mqVersion;

    private Date createTime;

    private Date updateTime;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getSpuId() {
        return spuId;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMqVersion() {
        return mqVersion;
    }

    public void setMqVersion(Integer mqVersion) {
        this.mqVersion = mqVersion;
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
        return "SkuStockLock{" +
                "orderId=" + orderId +
                ", skuId=" + skuId +
                ", spuId=" + spuId +
                ", count=" + count +
                ", status=" + status +
                ", mqVersion=" + mqVersion +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
