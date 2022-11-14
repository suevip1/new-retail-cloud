package com.zhihao.newretail.order.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderCouponsVO {

    private Integer id;

    private BigDecimal deno;

    private BigDecimal condition;

}
