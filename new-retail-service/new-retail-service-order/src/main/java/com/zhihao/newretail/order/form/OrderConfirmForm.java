package com.zhihao.newretail.order.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OrderConfirmForm {

    @NotBlank(message = "订单凭证不能为空")
    private String orderToken;

    @NotNull(message = "请选择收货地址")
    private Integer addressId;

    private Integer couponsId;

    public String getOrderToken() {
        return orderToken;
    }

    public void setOrderToken(String orderToken) {
        this.orderToken = orderToken;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getCouponsId() {
        return couponsId;
    }

    public void setCouponsId(Integer couponsId) {
        this.couponsId = couponsId;
    }

    @Override
    public String toString() {
        return "OrderConfirmForm{" +
                "orderToken='" + orderToken + '\'' +
                ", addressId=" + addressId +
                ", couponsId=" + couponsId +
                '}';
    }

}
