package com.zhihao.newretail.api.product.dto;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public class SkuStockLockApiDTO {

    private Integer spuId;

    private Integer skuId;

    private Long orderId;

    private Integer count;

    private Integer status;

    private Integer mqVersion;

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    @Override
    public String toString() {
        return "SkuStockLockApiDTO{" +
                "spuId=" + spuId +
                ", skuId=" + skuId +
                ", orderId=" + orderId +
                ", count=" + count +
                ", status=" + status +
                ", mqVersion=" + mqVersion +
                '}';
    }

}
