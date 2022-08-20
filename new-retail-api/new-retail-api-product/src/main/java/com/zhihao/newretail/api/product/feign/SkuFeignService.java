package com.zhihao.newretail.api.product.feign;

import com.zhihao.newretail.api.product.dto.SkuAddApiDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "new-retail-product", path = "/product")
public interface SkuFeignService {

    @PostMapping("/api/sku")
    void addSku(@Valid @RequestBody SkuAddApiDTO skuAddApiDTO);

}
