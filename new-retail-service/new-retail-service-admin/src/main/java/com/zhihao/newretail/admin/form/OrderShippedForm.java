package com.zhihao.newretail.admin.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OrderShippedForm {

    @NotNull(message = "订单号不能为空")
    private Long orderId;

    @NotBlank(message = "快递运单号不能为空")
    private String logisticsId;

    @NotBlank(message = "快递公司名称不能为空")
    private String logisticsCompany;

}
