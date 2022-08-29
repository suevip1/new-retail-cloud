package com.zhihao.newretail.api.product.fallback;

import com.zhihao.newretail.api.product.dto.SkuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SkuUpdateApiDTO;
import com.zhihao.newretail.api.product.feign.SkuFeignService;
import org.springframework.stereotype.Component;

@Component
public class SkuFeignFallback implements SkuFeignService {

    @Override
    public Integer addSku(SkuAddApiDTO skuAddApiDTO) {
        return null;
    }

    @Override
    public Integer updateSku(Integer skuId, SkuUpdateApiDTO skuUpdateApiDTO) {
        return null;
    }

    @Override
    public Integer deleteSku(Integer skuId) {
        return null;
    }

}
