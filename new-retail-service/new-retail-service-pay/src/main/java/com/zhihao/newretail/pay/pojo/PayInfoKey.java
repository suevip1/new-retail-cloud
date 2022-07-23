package com.zhihao.newretail.pay.pojo;

public class PayInfoKey {

    private Long orderId;

    private Integer userId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PayInfoKey{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                '}';
    }

}
