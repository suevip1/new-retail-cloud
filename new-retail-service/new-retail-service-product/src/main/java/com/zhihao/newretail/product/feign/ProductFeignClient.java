package com.zhihao.newretail.product.feign;

import com.zhihao.newretail.api.product.vo.GoodsApiVO;
import com.zhihao.newretail.api.product.vo.ProductApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.product.service.ProductService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class ProductFeignClient {

    @Autowired
    private ProductService productService;

    @PostMapping("/api/goods/list")
    public List<GoodsApiVO> listGoodsApiVOS(@RequestBody Set<Integer> idSet) {
        return productService.listGoodsApiVOS(idSet);
    }

    @GetMapping("/api/goods/{skuId}")
    public GoodsApiVO getGoodsApiVO(@PathVariable Integer skuId) {
        return productService.getGoodsApiVO(skuId);
    }

    @RequiresLogin
    @GetMapping("/api/product/list")
    public PageUtil<ProductApiVO> listProductApiVOS(@RequestParam(required = false) Integer categoryId,
                                                    @RequestParam(defaultValue = "!") Integer pageNum,
                                                    @RequestParam(defaultValue = "!") Integer pageSize) {
        PageUtil<ProductApiVO> pageData = productService.listProductApiVOS(categoryId, pageNum, pageSize);
        UserLoginContext.sysClean();
        return pageData;
    }

}
