package com.zhihao.newretail.api.order.vo;

import java.math.BigDecimal;
import java.util.Date;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public class OrderApiVO {

    private Long id;

    private String orderCode;

    private Integer userId;

    private BigDecimal amount;

    private BigDecimal actualAmount;

    private BigDecimal postage;

    private Integer couponsId;

    private Integer paymentType;

    private Integer status;

    private Integer isDelete;

    private Integer orderSharding;

    private Integer mqVersion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public BigDecimal getPostage() {
        return postage;
    }

    public void setPostage(BigDecimal postage) {
        this.postage = postage;
    }

    public Integer getCouponsId() {
        return couponsId;
    }

    public void setCouponsId(Integer couponsId) {
        this.couponsId = couponsId;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getOrderSharding() {
        return orderSharding;
    }

    public void setOrderSharding(Integer orderSharding) {
        this.orderSharding = orderSharding;
    }

    public Integer getMqVersion() {
        return mqVersion;
    }

    public void setMqVersion(Integer mqVersion) {
        this.mqVersion = mqVersion;
    }

    @Override
    public String toString() {
        return "OrderApiVO{" +
                "id=" + id +
                ", orderCode='" + orderCode + '\'' +
                ", userId=" + userId +
                ", amount=" + amount +
                ", actualAmount=" + actualAmount +
                ", postage=" + postage +
                ", couponsId=" + couponsId +
                ", paymentType=" + paymentType +
                ", status=" + status +
                ", isDelete=" + isDelete +
                ", orderSharding=" + orderSharding +
                ", mqVersion=" + mqVersion +
                '}';
    }

}
