package com.zhihao.newretail.product.feign;

import com.zhihao.newretail.api.product.dto.SkuStockLockApiDTO;
import com.zhihao.newretail.api.product.dto.SkuStockLockBatchApiDTO;
import com.zhihao.newretail.api.product.feign.StockFeignService;
import com.zhihao.newretail.api.product.vo.SkuStockApiVO;
import com.zhihao.newretail.product.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class StockFeignController implements StockFeignService {

    @Autowired
    private StockService stockService;

    @Override
    public List<SkuStockApiVO> listSkuStockApiVOS(Set<Integer> skuIdSet) {
        return stockService.listSkuStockApiVOS(skuIdSet);
    }

    @Override
    public void stockLock(SkuStockLockApiDTO skuStockLockApiDTO) {
        stockService.stockLock(skuStockLockApiDTO);
    }

    @Override
    public int batchStockLock(SkuStockLockBatchApiDTO skuStockLockBatchApiDTO) {
        List<SkuStockLockApiDTO> skuStockLockApiDTOList = skuStockLockBatchApiDTO.getSkuStockLockApiDTOList();
        return stockService.batchStockLock(skuStockLockApiDTOList);
    }

}
