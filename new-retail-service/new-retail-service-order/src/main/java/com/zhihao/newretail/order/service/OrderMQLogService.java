package com.zhihao.newretail.order.service;

import com.zhihao.newretail.order.pojo.OrderMQLog;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public interface OrderMQLogService {

    /*
     * 保存消息
     * */
    void insetMessage(Long messageId, String content, String exchange, String routingKey);

    /*
     * 更新消息
     * */
    void updateMessage(OrderMQLog orderMqLog);

    /*
     * 删除消息
     * */
    void deleteMessage(Long messageId);

    /*
     * 获取消息信息
     * */
    OrderMQLog getMQLog(Long messageId);

    /*
    * 获取消息唯一id
    * */
    Long getMessageId();

}
