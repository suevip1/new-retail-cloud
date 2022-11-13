package com.zhihao.newretail.rabbitmq.dto.order;

import lombok.Data;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Data
public class OrderCloseMQDTO {

    private Long orderNo;

    private Integer couponsId;

    private Integer mqVersion;

}
