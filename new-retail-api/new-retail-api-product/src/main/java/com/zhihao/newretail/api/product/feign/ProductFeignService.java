package com.zhihao.newretail.api.product.feign;

import com.zhihao.newretail.api.product.dto.SkuBatchApiDTO;
import com.zhihao.newretail.api.product.dto.SkuStockBatchApiDTO;
import com.zhihao.newretail.api.product.vo.SkuApiVO;
import com.zhihao.newretail.api.product.vo.SkuStockApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "new-retail-product", path = "/product")
public interface ProductFeignService {

    @PostMapping("/api/listSkus")
    List<SkuApiVO> listSkuApiVOs(@RequestBody SkuBatchApiDTO skuBatchApiDTO);

    @GetMapping("/api/sku/{skuId}")
    SkuApiVO getSkuApiVO(@PathVariable Integer skuId);

}
