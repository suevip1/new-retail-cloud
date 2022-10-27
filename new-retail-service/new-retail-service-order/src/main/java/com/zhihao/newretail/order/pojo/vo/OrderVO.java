package com.zhihao.newretail.order.pojo.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderVO {

    private Long id;

    private String orderCode;

    private BigDecimal amount;

    private BigDecimal actualAmount;

    private BigDecimal postage;

    private Integer couponsId;

    private Integer paymentType;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private OrderLogisticsInfoVO orderLogisticsInfoVO;

    private OrderAddressVO orderAddressVO;

    private OrderCouponsVO orderCouponsVO;

    private List<OrderItemVO> orderItemVOList;

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

    public OrderLogisticsInfoVO getOrderLogisticsInfoVO() {
        return orderLogisticsInfoVO;
    }

    public void setOrderLogisticsInfoVO(OrderLogisticsInfoVO orderLogisticsInfoVO) {
        this.orderLogisticsInfoVO = orderLogisticsInfoVO;
    }

    public OrderAddressVO getOrderAddressVO() {
        return orderAddressVO;
    }

    public void setOrderAddressVO(OrderAddressVO orderAddressVO) {
        this.orderAddressVO = orderAddressVO;
    }

    public OrderCouponsVO getOrderCouponsVO() {
        return orderCouponsVO;
    }

    public void setOrderCouponsVO(OrderCouponsVO orderCouponsVO) {
        this.orderCouponsVO = orderCouponsVO;
    }

    public List<OrderItemVO> getOrderItemVOList() {
        return orderItemVOList;
    }

    public void setOrderItemVOList(List<OrderItemVO> orderItemVOList) {
        this.orderItemVOList = orderItemVOList;
    }

    @Override
    public String toString() {
        return "OrderVO{" +
                "id=" + id +
                ", orderCode='" + orderCode + '\'' +
                ", amount=" + amount +
                ", actualAmount=" + actualAmount +
                ", postage=" + postage +
                ", couponsId=" + couponsId +
                ", paymentType=" + paymentType +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", orderLogisticsInfoVO=" + orderLogisticsInfoVO +
                ", orderAddressVO=" + orderAddressVO +
                ", orderCouponsVO=" + orderCouponsVO +
                ", orderItemVOList=" + orderItemVOList +
                '}';
    }

}
