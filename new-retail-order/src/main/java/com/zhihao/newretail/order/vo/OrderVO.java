package com.zhihao.newretail.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderVO {

    private Long id;

    private String orderCode;

    private BigDecimal amount;

    private BigDecimal actualAmount;

    private BigDecimal postage;

    private Integer couponsId;

    private Integer paymentType;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private OrderLogisticsInfoVO orderLogisticsInfoVO;

    private OrderAddressVO orderAddressVO;

    private OrderCouponsVO orderCouponsVO;

    private List<OrderItemVO> orderItemVOList;

}
