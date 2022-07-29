package com.zhihao.newretail.product.feign;

import com.zhihao.newretail.api.product.dto.SkuStockBatchApiDTO;
import com.zhihao.newretail.api.product.feign.ProductStockFeignService;
import com.zhihao.newretail.api.product.vo.SkuStockApiVO;
import com.zhihao.newretail.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductStockFeignController implements ProductStockFeignService {

    @Autowired
    private ProductService productService;

    @Override
    public List<SkuStockApiVO> listSkuStockApiVOs(SkuStockBatchApiDTO skuStockBatchApiDTO) {
        return productService.listSkuStockApiVOs(skuStockBatchApiDTO.getIdSet());
    }

}
