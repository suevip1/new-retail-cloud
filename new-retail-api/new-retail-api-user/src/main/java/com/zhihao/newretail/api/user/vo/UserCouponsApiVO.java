package com.zhihao.newretail.api.user.vo;

public class UserCouponsApiVO {

    private Integer id;

    private Integer couponsId;

    private Integer quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "UserCouponsApiVO{" +
                "id=" + id +
                ", couponsId=" + couponsId +
                ", quantity=" + quantity +
                '}';
    }

}
