package com.zhihao.newretail.api.product.fallback;

import com.zhihao.newretail.api.product.dto.SkuStockLockApiDTO;
import com.zhihao.newretail.api.product.feign.StockFeignService;
import com.zhihao.newretail.api.product.vo.SkuStockApiVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class StockFeignFallback implements StockFeignService {

    @Override
    public List<SkuStockApiVO> listSkuStockApiVOS(Set<Integer> skuIdSet) {
        return null;
    }

    @Override
    public void stockLock(SkuStockLockApiDTO skuStockLockApiDTO) {

    }

    @Override
    public Integer batchStockLock(List<SkuStockLockApiDTO> skuStockLockApiDTOList) {
        return null;
    }

}
