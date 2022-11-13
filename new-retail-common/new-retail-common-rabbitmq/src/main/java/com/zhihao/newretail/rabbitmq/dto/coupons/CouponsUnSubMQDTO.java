package com.zhihao.newretail.rabbitmq.dto.coupons;

import lombok.Data;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Data
public class CouponsUnSubMQDTO {

    private Integer couponsId;

    private Integer quantity;

    private Integer mqVersion;

}
