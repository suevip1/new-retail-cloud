package com.zhihao.newretail.api.product.feign;

import com.zhihao.newretail.api.product.dto.SlideAddApiDTO;
import com.zhihao.newretail.api.product.dto.SlideUpdateApiDTO;
import com.zhihao.newretail.api.product.fallback.SlideFeignFallback;
import com.zhihao.newretail.api.product.vo.SlideApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Primary
@FeignClient(name = "new-retail-product", path = "/new-retail-product", fallback = SlideFeignFallback.class)
public interface SlideFeignService {

    /*
    * 轮播图列表
    * */
    @GetMapping("/feign/slide/list")
    PageUtil<SlideApiVO> listSlideApiVOS(@RequestParam(required = false) Integer slideId,
                                         @RequestParam(defaultValue = "1") Integer pageNum,
                                         @RequestParam(defaultValue = "10") Integer pageSize);

    /*
    * 新增首页轮播图
    * */
    @PostMapping("/feign/slide")
    Integer addSlide(@RequestBody SlideAddApiDTO slideAddApiDTO);

    /*
    * 修改首页轮播图
    * */
    @PutMapping("/feign/slide/{slideId}")
    Integer updateSlide(@PathVariable Integer slideId, @RequestBody SlideUpdateApiDTO slideUpdateApiDTO);

    /*
    * 删除首页轮播图
    * */
    @DeleteMapping("/feign/slide/{slideId}")
    Integer deleteSlide(@PathVariable Integer slideId);

}
