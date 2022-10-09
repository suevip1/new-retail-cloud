package com.zhihao.newretail.pay.service;

import com.zhihao.newretail.pay.pojo.PayInfoMQLog;

import java.util.List;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public interface PayInfoMQLogService {

    /*
     * 保存消息
     * */
    int insetMessage(Long messageId, String content, String exchange, String routingKey);

    /*
     * 更新消息
     * */
    int updateMessage(PayInfoMQLog payInfoMqLog);

    /*
     * 删除消息
     * */
    void deleteMessage(Long messageId);

    /*
     * 获取消息信息
     * */
    PayInfoMQLog getMQLog(Long messageId);

    /*
    * 获取发送状态异常消息
    * */
    List<PayInfoMQLog> listPayInfoMQLogS(Integer status);

    /*
     * 获取消息唯一id
     * */
    Long getMessageId();

}
