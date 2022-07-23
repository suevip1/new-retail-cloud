package com.zhihao.newretail.user.pojo;

public class UserCoupons {

    private Integer id;

    private Integer userId;

    private Integer couponsId;

    private Integer quantity;

    private Integer mqVersion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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
        return "UserCoupons{" +
                "id=" + id +
                ", userId=" + userId +
                ", couponsId=" + couponsId +
                ", quantity=" + quantity +
                ", mqVersion=" + mqVersion +
                '}';
    }

}
