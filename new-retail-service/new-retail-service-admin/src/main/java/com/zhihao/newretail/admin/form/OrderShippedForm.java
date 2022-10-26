package com.zhihao.newretail.admin.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OrderShippedForm {

    @NotNull(message = "订单号不能为空")
    private Long orderId;

    @NotBlank(message = "快递运单号不能为空")
    private String logisticsId;

    @NotBlank(message = "快递公司名称不能为空")
    private String logisticsCompany;

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

}
