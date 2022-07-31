package com.zhihao.newretail.order.pojo.vo;

import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.api.user.vo.UserAddressApiVO;

import java.math.BigDecimal;
import java.util.List;

public class OrderSubmitVO {

    private List<UserAddressApiVO> userAddressApiVOList;

    private List<OrderItemSubmitVO> orderItemSubmitVOList;

    private List<CouponsApiVO> couponsApiVOList;

    private BigDecimal totalPrice;

    private String orderToken;

    public List<UserAddressApiVO> getUserAddressApiVOList() {
        return userAddressApiVOList;
    }

    public void setUserAddressApiVOList(List<UserAddressApiVO> userAddressApiVOList) {
        this.userAddressApiVOList = userAddressApiVOList;
    }

    public List<OrderItemSubmitVO> getOrderItemSubmitVOList() {
        return orderItemSubmitVOList;
    }

    public void setOrderItemSubmitVOList(List<OrderItemSubmitVO> orderItemSubmitVOList) {
        this.orderItemSubmitVOList = orderItemSubmitVOList;
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
        return "OrderSubmitVO{" +
                "userAddressApiVOList=" + userAddressApiVOList +
                ", orderItemSubmitVOList=" + orderItemSubmitVOList +
                ", couponsApiVOList=" + couponsApiVOList +
                ", totalPrice=" + totalPrice +
                ", orderToken='" + orderToken + '\'' +
                '}';
    }

}
