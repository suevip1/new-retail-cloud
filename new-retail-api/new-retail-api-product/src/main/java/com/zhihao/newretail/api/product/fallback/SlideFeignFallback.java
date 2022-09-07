package com.zhihao.newretail.api.product.fallback;

import com.zhihao.newretail.api.product.dto.SlideAddApiDTO;
import com.zhihao.newretail.api.product.dto.SlideUpdateApiDTO;
import com.zhihao.newretail.api.product.feign.SlideFeignService;
import org.springframework.stereotype.Component;

@Component
public class SlideFeignFallback implements SlideFeignService {

    @Override
    public Integer addSlide(SlideAddApiDTO slideAddApiDTO) {
        return null;
    }

    @Override
    public Integer updateSlide(Integer slideId, SlideUpdateApiDTO slideUpdateApiDTO) {
        return null;
    }

    @Override
    public Integer deleteSlide(Integer slideId) {
        return null;
    }

}
