package com.zhihao.newretail.rabbitmq.dto.stock;

import lombok.Data;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Data
public class StockUnLockMQDTO {

    private Long orderNo;

    private Integer mqVersion;

}
