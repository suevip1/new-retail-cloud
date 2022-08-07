package com.zhihao.newretail.rabbitmq.dto.pay;

import java.math.BigDecimal;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public class PayNotifyMQDTO {

    private Long orderNo;

    private Integer userId;

    private String platformNumber;

    private BigDecimal payAmount;

    private Integer payPlatform;

    private Integer status;

    private Integer mqVersion;

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(String platformNumber) {
        this.platformNumber = platformNumber;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(Integer payPlatform) {
        this.payPlatform = payPlatform;
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
        return "PayNotifyMQDTO{" +
                "orderNo=" + orderNo +
                ", userId=" + userId +
                ", platformNumber='" + platformNumber + '\'' +
                ", payAmount=" + payAmount +
                ", payPlatform=" + payPlatform +
                ", status=" + status +
                ", mqVersion=" + mqVersion +
                '}';
    }

}
