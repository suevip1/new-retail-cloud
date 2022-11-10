package com.zhihao.newretail.order.vo;

import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.api.user.vo.UserAddressApiVO;

import java.math.BigDecimal;
import java.util.List;

public class OrderCreateVO {

    private List<UserAddressApiVO> userAddressApiVOList;

    private List<OrderItemCreateVO> orderItemCreateVOList;

    private List<CouponsApiVO> couponsApiVOList;

    private BigDecimal totalPrice;

    private String orderToken;

    public List<UserAddressApiVO> getUserAddressApiVOList() {
        return userAddressApiVOList;
    }

    public void setUserAddressApiVOList(List<UserAddressApiVO> userAddressApiVOList) {
        this.userAddressApiVOList = userAddressApiVOList;
    }

    public List<OrderItemCreateVO> getOrderItemCreateVOList() {
        return orderItemCreateVOList;
    }

    public void setOrderItemCreateVOList(List<OrderItemCreateVO> orderItemCreateVOList) {
        this.orderItemCreateVOList = orderItemCreateVOList;
    }

    public List<CouponsApiVO> getCouponsApiVOList() {
        return couponsApiVOList;
    }

    public void setCouponsApiVOList(List<CouponsApiVO> couponsApiVOList) {
        this.couponsApiVOList = couponsApiVOList;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderToken() {
        return orderToken;
    }

    public void setOrderToken(String orderToken) {
        this.orderToken = orderToken;
    }

    @Override
    public String toString() {
        return "OrderCreateVO{" +
                "userAddressApiVOList=" + userAddressApiVOList +
                ", orderItemCreateVOList=" + orderItemCreateVOList +
                ", couponsApiVOList=" + couponsApiVOList +
                ", totalPrice=" + totalPrice +
                ", orderToken='" + orderToken + '\'' +
                '}';
    }

}
