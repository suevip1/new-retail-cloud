package com.zhihao.newretail.pay.jobhandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import com.zhihao.newretail.pay.pojo.PayInfoMQLog;
import com.zhihao.newretail.pay.service.PayInfoMQLogService;
import com.zhihao.newretail.rabbitmq.enums.MessageStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Component
public class PayXxlJob {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PayInfoMQLogService payInfoMQLogService;

    @XxlJob("payMessageSendErrorJobHandler")
    public void payMessageSendErrorJobHandler() {
        log.info("支付服务, XXL-JOB, 未发送状态消息处理.");
        List<PayInfoMQLog> payInfoMQLogList = payInfoMQLogService.listPayInfoMQLogS(MessageStatusEnum.NEW_MESSAGE.getCode());
        if (!CollectionUtils.isEmpty(payInfoMQLogList)) {
            for (PayInfoMQLog payInfoMQLog : payInfoMQLogList) {
                rabbitTemplate.convertAndSend(payInfoMQLog.getExchange(), payInfoMQLog.getRoutingKey(), payInfoMQLog.getContent(), new CorrelationData(String.valueOf(payInfoMQLog.getMessageId())));
            }
            log.info("支付服务, XXL-JOB, 未发送状态消息处理完成.");
        } else {
            log.info("支付服务, XXL-JOB, 未发送状态消息为空, 无需处理.");
        }
    }

}
