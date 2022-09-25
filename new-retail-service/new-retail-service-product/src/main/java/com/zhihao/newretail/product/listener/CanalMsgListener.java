package com.zhihao.newretail.product.listener;

import com.google.gson.*;
import com.rabbitmq.client.Channel;
import com.zhihao.newretail.product.consts.TableNameConst;
import com.zhihao.newretail.product.factory.ProductCacheSyncFactory;
import com.zhihao.newretail.rabbitmq.consts.RabbitMQConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@Component
public class CanalMsgListener {

    @Autowired
    private ProductCacheSyncFactory productCacheSyncFactory;

    private static final Gson gson = new GsonBuilder().create();

    @RabbitListener(queues = RabbitMQConst.CANAL_QUEUE)
    public void stockUnLockQueue(String msgStr, Message message, Channel channel) throws IOException {
        log.info("接受canal消息: {}", msgStr);
        JsonObject jsonObject = gson.fromJson(msgStr, JsonObject.class);
        String tableName = gson.fromJson(jsonObject.get("table"), String.class);
        JsonArray array = jsonObject.get("data").getAsJsonArray();
        for (JsonElement element : array) {
            if (!TableNameConst.TB_CATEGORY.equals(tableName)) {
                HashMap<String, Object> hashMap = gson.fromJson(element, HashMap.class);
                Integer spuId = Integer.valueOf(String.valueOf(hashMap.get("spu_id")));
                productCacheSyncFactory.productCacheSyncService(tableName).productCacheRemove(spuId);
            } else {
                productCacheSyncFactory.productCacheSyncService(tableName).productCacheRemove(null);
            }
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
