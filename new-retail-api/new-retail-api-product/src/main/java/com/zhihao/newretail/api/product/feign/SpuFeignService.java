package com.zhihao.newretail.api.product.feign;

import com.zhihao.newretail.api.product.dto.SpuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpuUpdateApiDTO;
import com.zhihao.newretail.api.product.fallback.SpuFeignFallback;
import com.zhihao.newretail.api.product.vo.SpuApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.*;

@Primary
@FeignClient(name = "new-retail-product", path = "/product", fallback = SpuFeignFallback.class)
public interface SpuFeignService {

    @GetMapping("/api/spu/{spuId}")
    SpuApiVO getSpuApiVO(@PathVariable Integer spuId);

    @PostMapping("/api/spu")
    Integer addSpu(@RequestBody SpuAddApiDTO spuAddApiDTO);

    @PutMapping("/api/spu/{spuId}")
    Integer updateSpu(@PathVariable Integer spuId, @RequestBody SpuUpdateApiDTO spuUpdateApiDTO);

    @DeleteMapping("/api/spu/{spuId}")
    Integer deleteSpu(@PathVariable Integer spuId);

}
