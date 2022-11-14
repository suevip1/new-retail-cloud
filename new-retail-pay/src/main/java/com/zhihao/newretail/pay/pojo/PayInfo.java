package com.zhihao.newretail.pay.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PayInfo {

    private Long orderId;

    private Integer userId;

    private Integer payInfoIndex;

    private BigDecimal payAmount;

    private Integer payPlatform;

    private Integer status;

    private String platformNumber;

    private Integer isDelete;

    private Integer payInfoSharding;

    private Integer mqVersion;

    private Date createTime;

    private Date updateTime;

}
