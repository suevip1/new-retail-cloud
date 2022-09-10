package com.zhihao.newretail.api.product.fallback;

import com.zhihao.newretail.api.product.dto.SlideAddApiDTO;
import com.zhihao.newretail.api.product.dto.SlideUpdateApiDTO;
import com.zhihao.newretail.api.product.feign.SlideFeignService;
import com.zhihao.newretail.api.product.vo.SlideApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import org.springframework.stereotype.Component;

@Component
public class SlideFeignFallback implements SlideFeignService {

    @Override
    public PageUtil<SlideApiVO> listSlideApiVOS(Integer slideId, Integer pageNum, Integer pageSize) {
        return null;
    }

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
