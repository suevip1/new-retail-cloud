package com.zhihao.newretail.api.product.feign;

import com.zhihao.newretail.api.product.dto.SlideAddApiDTO;
import com.zhihao.newretail.api.product.dto.SlideUpdateApiDTO;
import com.zhihao.newretail.api.product.fallback.SlideFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Primary
@FeignClient(name = "new-retail-product", path = "/product", fallback = SlideFeignFallback.class)
public interface SlideFeignService {

    /*
    * 新增首页轮播图
    * */
    @PostMapping("/api/slide")
    Integer addSlide(@RequestBody SlideAddApiDTO slideAddApiDTO);

    /*
    * 修改首页轮播图
    * */
    @PutMapping("/api/slide/{slideId}")
    Integer updateSlide(@PathVariable Integer slideId, @RequestBody SlideUpdateApiDTO slideUpdateApiDTO);

}
