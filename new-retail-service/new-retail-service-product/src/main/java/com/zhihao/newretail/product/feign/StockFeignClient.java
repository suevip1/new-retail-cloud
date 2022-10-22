package com.zhihao.newretail.product.feign;

import com.zhihao.newretail.api.product.dto.SkuStockLockApiDTO;
import com.zhihao.newretail.api.product.vo.SkuStockApiVO;
import com.zhihao.newretail.product.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/feign")
public class StockFeignClient {

    @Autowired
    private StockService stockService;

    @PostMapping("/skuStock/list")
    public List<SkuStockApiVO> listSkuStockApiVOS(@RequestBody Set<Integer> skuIdSet) {
        return stockService.listSkuStockApiVOS(skuIdSet);
    }

    @PostMapping("/skuStock/lock")
    public void stockLock(@RequestBody SkuStockLockApiDTO skuStockLockApiDTO) {
        stockService.stockLock(skuStockLockApiDTO);
    }

    @PostMapping("/skuStock/batchLock")
    public Integer batchStockLock(@RequestBody List<SkuStockLockApiDTO> skuStockLockApiDTOList) {
        return stockService.batchStockLock(skuStockLockApiDTOList);
    }

}
