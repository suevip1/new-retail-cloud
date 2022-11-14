package com.zhihao.newretail.user.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserAddressAddForm {

    @NotBlank(message = "收货人姓名不能为空")
    private String name;

    @NotBlank(message = "收货人手机号码不能为空")
    private String tel;

    @NotBlank(message = "收货人地址不能为空")
    private String address;

    private String addressDetail;

    private Integer isPrime;

}
