package com.zhihao.newretail.api.product.feign;

import com.zhihao.newretail.api.product.fallback.ProductFeignFallback;
import com.zhihao.newretail.api.product.vo.GoodsApiVO;
import com.zhihao.newretail.api.product.vo.ProductApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Primary
@FeignClient(name = "new-retail-product", path = "/product", fallback = ProductFeignFallback.class)
public interface ProductFeignService {

    @PostMapping("/feign/goods/list")
    List<GoodsApiVO> listGoodsApiVOS(@RequestBody Set<Integer> idSet);

    @GetMapping("/feign/goods/{skuId}")
    GoodsApiVO getGoodsApiVO(@PathVariable Integer skuId);

    @GetMapping("/feign/product/list")
    PageUtil<ProductApiVO> listProductApiVOS(@RequestParam(required = false) Integer categoryId,
                                             @RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize);

}
