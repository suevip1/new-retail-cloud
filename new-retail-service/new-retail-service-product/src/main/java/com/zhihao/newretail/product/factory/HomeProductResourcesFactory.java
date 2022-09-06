package com.zhihao.newretail.product.factory;

import com.zhihao.newretail.product.service.HomeProductResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class HomeProductResourcesFactory {

    @Autowired
    private final Map<String, HomeProductResourcesService> map = new ConcurrentHashMap<>(3);

    public HomeProductResourcesService getResources(String type) {
        return map.get(type);
    }

}
