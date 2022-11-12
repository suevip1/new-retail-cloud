package com.zhihao.newretail.order.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order {

    private Long id;

    private Integer orderIndex;

    private String orderCode;

    private Integer userId;

    private BigDecimal amount;

    private BigDecimal actualAmount;

    private BigDecimal postage;

    private Integer couponsId;

    private Integer paymentType;

    private Integer status;

    private Integer isDelete;

    private Integer orderSharding;

    private Integer mqVersion;

    private Date createTime;

    private Date updateTime;

    private OrderAddress orderAddress;

}
