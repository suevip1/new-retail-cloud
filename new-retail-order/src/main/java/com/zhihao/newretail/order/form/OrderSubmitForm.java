package com.zhihao.newretail.order.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OrderSubmitForm {

    @NotBlank(message = "订单凭证不能为空")
    private String orderToken;

    @NotNull(message = "请选择收货地址")
    private Integer addressId;

    private Integer couponsId;

}
