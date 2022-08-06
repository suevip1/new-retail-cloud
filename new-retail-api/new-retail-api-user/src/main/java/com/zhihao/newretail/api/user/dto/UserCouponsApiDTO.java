package com.zhihao.newretail.api.user.dto;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public class UserCouponsApiDTO {

    private Integer couponsId;

    private Integer quantity;

    private Integer mqVersion;

    public Integer getCouponsId() {
        return couponsId;
    }

    public void setCouponsId(Integer couponsId) {
        this.couponsId = couponsId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getMqVersion() {
        return mqVersion;
    }

    public void setMqVersion(Integer mqVersion) {
        this.mqVersion = mqVersion;
    }

    @Override
    public String toString() {
        return "UserCouponsApiDTO{" +
                "couponsId=" + couponsId +
                ", quantity=" + quantity +
                ", mqVersion=" + mqVersion +
                '}';
    }

}
