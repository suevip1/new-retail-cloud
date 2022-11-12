package com.zhihao.newretail.api.pay.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PayInfoApiVO {

    private Long orderId;

    private Integer userId;

    private Integer payInfoIndex;

    private BigDecimal payAmount;

    private Integer payPlatform;

    private Integer status;

    private String platformNumber;

    private Date createTime;

    private Date updateTime;

}
