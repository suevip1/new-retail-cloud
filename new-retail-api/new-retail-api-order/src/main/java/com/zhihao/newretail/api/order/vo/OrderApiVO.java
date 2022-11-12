package com.zhihao.newretail.api.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderApiVO {

    private Long id;

    private Integer orderIndex;

    private String orderCode;

    private Integer userId;

    private BigDecimal amount;

    private BigDecimal actualAmount;

    private BigDecimal postage;

    private Integer couponsId;

    private BigDecimal deno;

    private BigDecimal condition;

    private Integer paymentType;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private OrderUserApiVO orderUserApiVO;

    private OrderLogisticsInfoApiVO orderLogisticsInfoApiVO;

    private OrderAddressApiVO orderAddressApiVO;

    private List<OrderItemApiVO> orderItemApiVOList;

}
