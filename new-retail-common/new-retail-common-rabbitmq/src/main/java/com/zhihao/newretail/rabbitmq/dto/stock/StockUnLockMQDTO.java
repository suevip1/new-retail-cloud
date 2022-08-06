package com.zhihao.newretail.rabbitmq.dto.stock;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public class StockUnLockMQDTO {

    private Long orderNo;

    private Integer mqVersion;

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getMqVersion() {
        return mqVersion;
    }

    public void setMqVersion(Integer mqVersion) {
        this.mqVersion = mqVersion;
    }

}
