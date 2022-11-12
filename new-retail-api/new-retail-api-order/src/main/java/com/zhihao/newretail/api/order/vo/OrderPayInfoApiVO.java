package com.zhihao.newretail.api.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Data
public class OrderPayInfoApiVO {

    private Long id;

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

}
