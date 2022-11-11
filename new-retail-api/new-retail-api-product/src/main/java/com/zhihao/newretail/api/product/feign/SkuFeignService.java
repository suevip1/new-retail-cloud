package com.zhihao.newretail.api.product.feign;

import com.zhihao.newretail.api.product.dto.SkuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SkuUpdateApiDTO;
import com.zhihao.newretail.api.product.fallback.SkuFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Primary
@FeignClient(name = "new-retail-product", path = "/new-retail-product", fallback = SkuFeignFallback.class)
public interface SkuFeignService {

    @PostMapping("/feign/sku")
    Integer addSku(@RequestBody SkuAddApiDTO skuAddApiDTO);

    @PutMapping("/feign/sku/{skuId}")
    Integer updateSku(@PathVariable Integer skuId, @RequestBody SkuUpdateApiDTO skuUpdateApiDTO);

    @DeleteMapping("/feign/sku/{skuId}")
    Integer deleteSku(@PathVariable Integer skuId);

}
