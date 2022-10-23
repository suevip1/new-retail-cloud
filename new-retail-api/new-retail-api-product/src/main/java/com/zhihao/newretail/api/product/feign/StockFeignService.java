package com.zhihao.newretail.api.product.feign;

import com.zhihao.newretail.api.product.dto.SkuStockLockApiDTO;
import com.zhihao.newretail.api.product.fallback.StockFeignFallback;
import com.zhihao.newretail.api.product.vo.SkuStockApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@Primary
@FeignClient(name = "new-retail-product", path = "/new-retail-product", fallback = StockFeignFallback.class)
public interface StockFeignService {

    /*
    * 获取库存信息
    * */
    @PostMapping("/feign/sku-stock/list")
    List<SkuStockApiVO> listSkuStockApiVOS(@RequestBody Set<Integer> skuIdSet);

    /*
    * 锁定商品库存
    * */
    @PostMapping("/feign/sku-stock/lock")
    void stockLock(@RequestBody SkuStockLockApiDTO skuStockLockApiDTO);

    /*
    * 批量锁定商品库存
    * */
    @PostMapping("/feign/sku-stock/batch-lock")
    Integer batchStockLock(@RequestBody List<SkuStockLockApiDTO> skuStockLockApiDTOList);

}
