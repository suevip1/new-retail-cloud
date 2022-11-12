package com.zhihao.newretail.order.pojo;

import lombok.Data;

@Data
public class OrderLogisticsInfo {

    private Long orderId;

    private String logisticsId;

    private String logisticsCompany;

    private Integer orderLogisticsInfoSharding;

}
