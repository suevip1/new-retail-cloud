package com.zhihao.newretail.order.jobhandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import com.zhihao.newretail.order.pojo.OrderMQLog;
import com.zhihao.newretail.order.service.OrderMQLogService;
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
public class OrderXxlJob {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderMQLogService orderMQLogService;

    @XxlJob("orderMessageSendErrorJobHandler")
    public void orderMessageSendErrorJobHandler() {
        log.info("XXL-JOB, 未发送状态消息处理.");
        List<OrderMQLog> orderMQLogList = orderMQLogService.listOrderMQLogS(MessageStatusEnum.NEW_MESSAGE.getCode());
        if (!CollectionUtils.isEmpty(orderMQLogList)) {
            for (OrderMQLog orderMQLog : orderMQLogList) {
                rabbitTemplate.convertAndSend(orderMQLog.getExchange(), orderMQLog.getRoutingKey(), orderMQLog.getContent(), new CorrelationData(String.valueOf(orderMQLog.getMessageId())));
            }
            log.info("XXL-JOB, 未发送状态消息处理完成.");
        } else {
            log.info("XXL-JOB, 未发送状态消息为空, 无需处理.");
        }
    }

}
