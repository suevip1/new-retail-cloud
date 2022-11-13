package com.zhihao.newretail.admin.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CouponsForm {

    @NotNull(message = "优惠券面值不能为空")
    private BigDecimal deno;

    @NotNull(message = "优惠券使用条件不能为空")
    private BigDecimal condition;

    private Date startDate;

    private Date endDate;

    @NotNull(message = "优惠券发放数量不能为空")
    private Integer maxNum;

}
