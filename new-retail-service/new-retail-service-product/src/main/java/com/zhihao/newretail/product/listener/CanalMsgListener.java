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
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@Component
public class CanalMsgListener {

    @Autowired
    private ProductCacheSyncFactory productCacheSyncFactory;

    private static final Gson gson = new GsonBuilder().create();

    @RabbitListener(queues = RabbitMQConst.CANAL_PRODUCT_QUEUE)
    public void canalMsgQueue(String msgStr, Message message, Channel channel) throws IOException {
        log.info("商品服务，接收canal消息: {}", msgStr);
        JsonObject jsonObject = gson.fromJson(msgStr, JsonObject.class);
        String tableName = gson.fromJson(jsonObject.get("table"), String.class);
        JsonArray data = jsonObject.get("data").getAsJsonArray();
        for (JsonElement element : data) {
            if (!TableNameConst.TB_CATEGORY.equals(tableName)) {
                HashMap<String, Object> dataMap = gson.fromJson(element, HashMap.class);
                Integer spuId = Integer.valueOf(String.valueOf(dataMap.get("spu_id")));
                productCacheSyncFactory.productCacheSyncService(tableName).productCacheRemove(spuId);
                JsonArray old = jsonObject.get("old").getAsJsonArray();
                for (JsonElement oldData : old) {
                    HashMap<String, Object> oldDataMap = gson.fromJson(oldData, HashMap.class);
                    Object oldSpuId = oldDataMap.get("spu_id");
                    if (!ObjectUtils.isEmpty(oldSpuId)) {
                        productCacheSyncFactory.productCacheSyncService(tableName).productCacheRemove(Integer.valueOf(String.valueOf(oldSpuId)));
                    }
                }
            } else {
                productCacheSyncFactory.productCacheSyncService(tableName).productCacheRemove(null);
            }
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
