package com.zhihao.newretail.product.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Coupons {

    private Integer id;

    private BigDecimal deno;

    private BigDecimal condition;

    private Date startDate;

    private Date endDate;

    private Integer maxNum;

    private Integer isSaleable;

    private Integer isDelete;

}
