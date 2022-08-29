package com.zhihao.newretail.api.product.fallback;

import com.zhihao.newretail.api.product.dto.SpecParamAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpecParamUpdateApiDTO;
import com.zhihao.newretail.api.product.feign.SpecParamFeignService;
import com.zhihao.newretail.api.product.vo.SpecParamApiVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpecParamFeignFallback implements SpecParamFeignService {

    @Override
    public List<SpecParamApiVO> listSpecParamApiVOS(Integer categoryId) {
        return null;
    }

    @Override
    public Integer addSpecParam(SpecParamAddApiDTO specParamAddApiDTO) {
        return null;
    }

    @Override
    public Integer updateSpecParam(Integer specParamId, SpecParamUpdateApiDTO specParamUpdateApiDTO) {
        return null;
    }

    @Override
    public Integer deleteSpecParam(Integer specParamId) {
        return null;
    }

}
