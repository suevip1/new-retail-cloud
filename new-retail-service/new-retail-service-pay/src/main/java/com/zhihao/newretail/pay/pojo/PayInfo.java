package com.zhihao.newretail.pay.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class PayInfo extends PayInfoKey {

    private Integer payInfoIndex;

    private BigDecimal payAmount;

    private Integer payPlatform;

    private Integer status;

    private String platformNumber;

    private Integer isDelete;

    private Integer payInfoSharding;

    private Integer mqVersion;

    private Date createTime;

    private Date updateTime;

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
        this.platformNumber = platformNumber == null ? null : platformNumber.trim();
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getPayInfoSharding() {
        return payInfoSharding;
    }

    public void setPayInfoSharding(Integer payInfoSharding) {
        this.payInfoSharding = payInfoSharding;
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
        return "PayInfo{" +
                "payInfoIndex=" + payInfoIndex +
                ", payAmount=" + payAmount +
                ", payPlatform=" + payPlatform +
                ", status=" + status +
                ", platformNumber='" + platformNumber + '\'' +
                ", isDelete=" + isDelete +
                ", payInfoSharding=" + payInfoSharding +
                ", mqVersion=" + mqVersion +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
