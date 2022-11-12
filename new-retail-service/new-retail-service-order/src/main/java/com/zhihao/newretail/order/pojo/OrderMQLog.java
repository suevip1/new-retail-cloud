package com.zhihao.newretail.order.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class OrderMQLog {

    private Long messageId;

    private String content;

    private String exchange;

    private String routingKey;

    private String queueName;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}
