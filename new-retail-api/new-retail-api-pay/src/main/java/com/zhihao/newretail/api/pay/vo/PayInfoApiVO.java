package com.zhihao.newretail.api.pay.vo;

import java.math.BigDecimal;
import java.util.Date;

public class PayInfoApiVO {

    private Long orderId;

    private Integer userId;

    private Integer payInfoIndex;

    private BigDecimal payAmount;

    private Integer payPlatform;

    private Integer status;

    private String platformNumber;

    private Date createTime;

    private Date updateTime;

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

    public Integer getPayInfoIndex() {
        return payInfoIndex;
    }

    public void setPayInfoIndex(Integer payInfoIndex) {
        this.payInfoIndex = payInfoIndex;
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

    public String getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(String platformNumber) {
        this.platformNumber = platformNumber;
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
        return "PayInfoApiVO{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", payInfoIndex=" + payInfoIndex +
                ", payAmount=" + payAmount +
                ", payPlatform=" + payPlatform +
                ", status=" + status +
                ", platformNumber='" + platformNumber + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
