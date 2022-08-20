package com.zhihao.newretail.product.feign;

import com.zhihao.newretail.api.product.feign.ProductFeignService;
import com.zhihao.newretail.api.product.vo.GoodsApiVO;
import com.zhihao.newretail.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class ProductFeignController implements ProductFeignService {

    @Autowired
    private ProductService productService;

    @Override
    public List<GoodsApiVO> listGoodsApiVOS(Set<Integer> idSet) {
        return productService.listGoodsApiVOS(idSet);
    }

    @Override
    public GoodsApiVO getGoodsApiVO(Integer skuId) {
        return productService.getGoodsApiVO(skuId);
    }

}
