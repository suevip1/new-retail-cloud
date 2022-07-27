package com.zhihao.newretail.product.feign;

import com.zhihao.newretail.api.product.dto.SkuBatchApiDTO;
import com.zhihao.newretail.api.product.feign.ProductFeignService;
import com.zhihao.newretail.api.product.vo.SkuApiVO;
import com.zhihao.newretail.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductFeignController implements ProductFeignService {

    @Autowired
    private ProductService productService;

    @Override
    public List<SkuApiVO> listSkuApiVOs(SkuBatchApiDTO skuBatchApiDTO) {
        return productService.listSkuApiVOs(skuBatchApiDTO.getIdSet());
    }

    @Override
    public SkuApiVO getSkuApiVO(Integer skuId) {
        return productService.getSkuApiVO(skuId);
    }

}
