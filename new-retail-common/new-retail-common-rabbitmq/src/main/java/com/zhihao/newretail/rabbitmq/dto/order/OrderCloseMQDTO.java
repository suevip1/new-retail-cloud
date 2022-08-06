package com.zhihao.newretail.rabbitmq.dto.order;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public class OrderCloseMQDTO {

    private Long orderNo;

    private Integer couponsId;

    private Integer mqVersion;

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getCouponsId() {
        return couponsId;
    }

    public void setCouponsId(Integer couponsId) {
        this.couponsId = couponsId;
    }

    public Integer getMqVersion() {
        return mqVersion;
    }

    public void setMqVersion(Integer mqVersion) {
        this.mqVersion = mqVersion;
    }

    @Override
    public String toString() {
        return "OrderCloseMQDTO{" +
                "orderNo=" + orderNo +
                ", couponsId=" + couponsId +
                ", mqVersion=" + mqVersion +
                '}';
    }

}
