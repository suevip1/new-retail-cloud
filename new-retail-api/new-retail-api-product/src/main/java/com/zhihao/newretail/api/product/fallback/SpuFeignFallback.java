package com.zhihao.newretail.api.product.fallback;

import com.zhihao.newretail.api.product.dto.SpuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpuUpdateApiDTO;
import com.zhihao.newretail.api.product.feign.SpuFeignService;
import com.zhihao.newretail.api.product.vo.SpuApiVO;
import org.springframework.stereotype.Component;

@Component
public class SpuFeignFallback implements SpuFeignService {

    @Override
    public SpuApiVO getSpuApiVO(Integer spuId) {
        return null;
    }

    @Override
    public Integer addSpu(SpuAddApiDTO spuAddApiDTO) {
        return null;
    }

    @Override
    public Integer updateSpu(Integer spuId, SpuUpdateApiDTO spuUpdateApiDTO) {
        return null;
    }

    @Override
    public Integer deleteSpu(Integer spuId) {
        return null;
    }

}
