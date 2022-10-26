package com.zhihao.newretail.order.pojo;

public class OrderLogisticsInfo {

    private Long orderId;

    private String logisticsId;

    private String logisticsCompany;

    private Integer orderLogisticsInfoSharding;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public Integer getOrderLogisticsInfoSharding() {
        return orderLogisticsInfoSharding;
    }

    public void setOrderLogisticsInfoSharding(Integer orderLogisticsInfoSharding) {
        this.orderLogisticsInfoSharding = orderLogisticsInfoSharding;
    }

    @Override
    public String toString() {
        return "OrderLogisticsInfo{" +
                "orderId=" + orderId +
                ", logisticsId='" + logisticsId + '\'' +
                ", logisticsCompany='" + logisticsCompany + '\'' +
                ", orderLogisticsInfoSharding=" + orderLogisticsInfoSharding +
                '}';
    }

}
