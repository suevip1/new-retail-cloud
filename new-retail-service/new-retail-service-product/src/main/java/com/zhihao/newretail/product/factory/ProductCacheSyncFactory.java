package com.zhihao.newretail.product.factory;

import com.zhihao.newretail.product.service.ProductCacheSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProductCacheSyncFactory {

    @Autowired
    private final Map<String, ProductCacheSyncService> map = new ConcurrentHashMap<>(4);

    public ProductCacheSyncService productCacheSyncService(String type) {
        return map.get(type);
    }

}
