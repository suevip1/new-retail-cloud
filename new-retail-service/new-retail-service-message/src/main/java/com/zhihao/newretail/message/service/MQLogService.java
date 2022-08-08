package com.zhihao.newretail.message.service;

import com.zhihao.newretail.message.pojo.MQLog;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public interface MQLogService {

    /*
     * 保存消息
     * */
    void insetMessage(MQLog mqLog);

    /*
     * 更新消息
     * */
    void updateMessage(MQLog mqLog);

    /*
     * 删除消息
     * */
    void deleteMessage(Long messageId);

    /*
     * 获取消息信息
     * */
    MQLog getMQLog(Long messageId);

}
