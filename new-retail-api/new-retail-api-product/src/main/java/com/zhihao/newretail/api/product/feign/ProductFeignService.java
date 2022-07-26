package com.zhihao.newretail.api.product.feign;

import com.zhihao.newretail.api.product.dto.ProductBatchApiDTO;
import com.zhihao.newretail.api.product.dto.SkuBatchApiDTO;
import com.zhihao.newretail.api.product.vo.ProductApiVO;
import com.zhihao.newretail.api.product.vo.SkuApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "new-retail-product", path = "/product")
public interface ProductFeignService {

    @PostMapping("/listSkus")
    List<SkuApiVO> listSkuApiVOs(@RequestBody SkuBatchApiDTO skuBatchApiDTO);

    @PostMapping("/listProducts")
    List<ProductApiVO> listProductApiVOs(@RequestBody ProductBatchApiDTO productBatchApiDTO);

}
