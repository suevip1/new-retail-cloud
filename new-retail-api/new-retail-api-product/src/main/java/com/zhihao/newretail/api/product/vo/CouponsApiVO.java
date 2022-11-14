package com.zhihao.newretail.api.product.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CouponsApiVO {

    private Integer id;

    private BigDecimal deno;

    private BigDecimal condition;

    private Date startDate;

    private Date endDate;

    private Integer isSaleable;

}
