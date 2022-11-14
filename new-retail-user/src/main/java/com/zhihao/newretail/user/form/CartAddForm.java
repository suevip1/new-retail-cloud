package com.zhihao.newretail.user.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CartAddForm {

    @NotNull(message = "商品规格不能为空")
    private Integer skuId;

    @NotNull(message = "数量不能为空")
    private Integer quantity;

    private Boolean selected = true;

}
