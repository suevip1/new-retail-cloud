package com.zhihao.newretail.api.order.vo;

public class OrderLogisticsInfoApiVO {

    private String logisticsId;

    private String logisticsCompany;

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

    @Override
    public String toString() {
        return "OrderLogisticsInfoApiVO{" +
                "logisticsId='" + logisticsId + '\'' +
                ", logisticsCompany='" + logisticsCompany + '\'' +
                '}';
    }

}
