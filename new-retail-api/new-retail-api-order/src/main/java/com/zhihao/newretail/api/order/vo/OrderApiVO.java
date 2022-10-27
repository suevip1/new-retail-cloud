package com.zhihao.newretail.api.order.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderApiVO {

    private Long id;

    private Integer orderIndex;

    private String orderCode;

    private Integer userId;

    private BigDecimal amount;

    private BigDecimal actualAmount;

    private BigDecimal postage;

    private Integer couponsId;

    private BigDecimal deno;

    private BigDecimal condition;

    private Integer paymentType;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private OrderUserApiVO orderUserApiVO;

    private OrderLogisticsInfoApiVO orderLogisticsInfoApiVO;

    private OrderAddressApiVO orderAddressApiVO;

    private List<OrderItemApiVO> orderItemApiVOList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
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

    public BigDecimal getDeno() {
        return deno;
    }

    public void setDeno(BigDecimal deno) {
        this.deno = deno;
    }

    public BigDecimal getCondition() {
        return condition;
    }

    public void setCondition(BigDecimal condition) {
        this.condition = condition;
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

    public OrderUserApiVO getOrderUserApiVO() {
        return orderUserApiVO;
    }

    public void setOrderUserApiVO(OrderUserApiVO orderUserApiVO) {
        this.orderUserApiVO = orderUserApiVO;
    }

    public OrderLogisticsInfoApiVO getOrderLogisticsInfoApiVO() {
        return orderLogisticsInfoApiVO;
    }

    public void setOrderLogisticsInfoApiVO(OrderLogisticsInfoApiVO orderLogisticsInfoApiVO) {
        this.orderLogisticsInfoApiVO = orderLogisticsInfoApiVO;
    }

    public OrderAddressApiVO getOrderAddressApiVO() {
        return orderAddressApiVO;
    }

    public void setOrderAddressApiVO(OrderAddressApiVO orderAddressApiVO) {
        this.orderAddressApiVO = orderAddressApiVO;
    }

    public List<OrderItemApiVO> getOrderItemApiVOList() {
        return orderItemApiVOList;
    }

    public void setOrderItemApiVOList(List<OrderItemApiVO> orderItemApiVOList) {
        this.orderItemApiVOList = orderItemApiVOList;
    }

    @Override
    public String toString() {
        return "OrderApiVO{" +
                "id=" + id +
                ", orderIndex=" + orderIndex +
                ", orderCode='" + orderCode + '\'' +
                ", userId=" + userId +
                ", amount=" + amount +
                ", actualAmount=" + actualAmount +
                ", postage=" + postage +
                ", couponsId=" + couponsId +
                ", deno=" + deno +
                ", condition=" + condition +
                ", paymentType=" + paymentType +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", orderUserApiVO=" + orderUserApiVO +
                ", orderLogisticsInfoApiVO=" + orderLogisticsInfoApiVO +
                ", orderAddressApiVO=" + orderAddressApiVO +
                ", orderItemApiVOList=" + orderItemApiVOList +
                '}';
    }

}
