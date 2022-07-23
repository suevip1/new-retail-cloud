package com.zhihao.newretail.order.pojo;

public class OrderItemKey {

    private Long orderId;

    private Integer skuId;

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

    @Override
    public String toString() {
        return "OrderItemKey{" +
                "orderId=" + orderId +
                ", skuId=" + skuId +
                '}';
    }

}
