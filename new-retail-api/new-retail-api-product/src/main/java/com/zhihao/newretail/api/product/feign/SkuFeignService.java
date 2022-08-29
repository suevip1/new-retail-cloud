package com.zhihao.newretail.api.product.feign;

import com.zhihao.newretail.api.product.dto.SkuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SkuUpdateApiDTO;
import com.zhihao.newretail.api.product.fallback.SkuFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.*;

@Primary
@FeignClient(name = "new-retail-product", path = "/product", fallback = SkuFeignFallback.class)
public interface SkuFeignService {

    @PostMapping("/api/sku")
    Integer addSku(@RequestBody SkuAddApiDTO skuAddApiDTO);

    @PutMapping("/api/sku/{skuId}")
    Integer updateSku(@PathVariable Integer skuId, @RequestBody SkuUpdateApiDTO skuUpdateApiDTO);

    @DeleteMapping("/api/sku/{skuId}")
    Integer deleteSku(@PathVariable Integer skuId);

}
