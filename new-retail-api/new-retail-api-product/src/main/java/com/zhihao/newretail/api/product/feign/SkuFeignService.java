package com.zhihao.newretail.api.product.feign;

import com.zhihao.newretail.api.product.dto.SkuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SkuUpdateApiDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(name = "new-retail-product", path = "/product")
public interface SkuFeignService {

    @PostMapping("/api/sku")
    void addSku(@Valid @RequestBody SkuAddApiDTO skuAddApiDTO);

    @PutMapping("/api/sku/{skuId}")
    void updateSku(@PathVariable Integer skuId, @Valid @RequestBody SkuUpdateApiDTO skuUpdateApiDTO);

    @DeleteMapping("/api/sku/{skuId}")
    void deleteSku(@PathVariable Integer skuId);

}
