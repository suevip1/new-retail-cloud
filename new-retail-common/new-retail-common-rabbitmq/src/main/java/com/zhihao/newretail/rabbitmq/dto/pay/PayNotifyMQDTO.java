package com.zhihao.newretail.rabbitmq.dto.pay;

import lombok.Data;

import java.math.BigDecimal;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Data
public class PayNotifyMQDTO {

    private Long orderNo;

    private Integer userId;

    private String platformNumber;

    private BigDecimal payAmount;

    private Integer payPlatform;

    private Integer status;

    private Integer mqVersion;

}
