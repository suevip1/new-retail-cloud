package com.zhihao.newretail.product.listener;

import com.rabbitmq.client.Channel;
import com.zhihao.newretail.core.util.GsonUtil;
import com.zhihao.newretail.product.enums.SkuStockTypeEnum;
import com.zhihao.newretail.product.pojo.SkuStockLock;
import com.zhihao.newretail.product.service.StockService;
import com.zhihao.newretail.rabbitmq.consts.RabbitMQConst;
import com.zhihao.newretail.rabbitmq.dto.stock.StockSubLockMQDTO;
import com.zhihao.newretail.rabbitmq.dto.stock.StockUnLockMQDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Slf4j
@Component
public class OrderNotifyMsgListener {

    @Autowired
    private StockService stockService;

    /*
    * 订单关闭、分布式事务解锁库存
    * */
    @RabbitListener(queues = RabbitMQConst.ORDER_STOCK_UNLOCK_QUEUE)
    public void stockUnLockQueue(String msgStr, Message message, Channel channel) throws IOException {
        StockUnLockMQDTO stockUnLockMQDTO = GsonUtil.json2Obj(msgStr, StockUnLockMQDTO.class);
        Long orderNo = stockUnLockMQDTO.getOrderNo();
        Integer version = stockUnLockMQDTO.getMqVersion();

        List<SkuStockLock> skuStockLockList = stockService.listSkuStockLocks(orderNo);
        List<SkuStockLock> skuStockLocks = buildSkuStockLockList(skuStockLockList, version);
        try {
            stockService.updateStockByType(orderNo, skuStockLocks, SkuStockTypeEnum.UN_LOCK);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("当前时间:{},订单号:{},解锁库存", new Date(), orderNo);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }

    /*
    * 订单支付成功，删减库存
    * */
    @RabbitListener(queues = RabbitMQConst.ORDER_STOCK_SUB_QUEUE)
    public void stockSubQueue(String msgStr, Message message, Channel channel) throws IOException {
        StockSubLockMQDTO stockSubLockMQDTO = GsonUtil.json2Obj(msgStr, StockSubLockMQDTO.class);
        Long orderNo = stockSubLockMQDTO.getOrderNo();
        Integer version = stockSubLockMQDTO.getMqVersion();

        List<SkuStockLock> skuStockLockList = stockService.listSkuStockLocks(orderNo);
        List<SkuStockLock> skuStockLocks = buildSkuStockLockList(skuStockLockList, version);
        try {
            stockService.updateStockByType(orderNo, skuStockLocks, SkuStockTypeEnum.SUB);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("当前时间:{},订单号:{},删减库存", new Date(), orderNo);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }

    private List<SkuStockLock> buildSkuStockLockList(List<SkuStockLock> skuStockLockList, Integer version) {
        List<SkuStockLock> skuStockLocks = new ArrayList<>();
        if (!CollectionUtils.isEmpty(skuStockLockList)) {
            for (SkuStockLock skuStockLock : skuStockLockList) {
                AtomicInteger skuStockVersion = new AtomicInteger(skuStockLock.getMqVersion());
                if (skuStockVersion.compareAndSet(version, skuStockVersion.get() + RabbitMQConst.CONSUME_VERSION)) {
                    skuStockLock.setMqVersion(skuStockVersion.get());
                    skuStockLocks.add(skuStockLock);
                }
            }
        }
        return skuStockLocks;
    }

}
