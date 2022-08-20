package com.zhihao.newretail.product.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.product.pojo.vo.ProductDetailVO;
import com.zhihao.newretail.product.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class ProductController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/detail/{spuId}")
    public R productDetail(@PathVariable Integer spuId) throws ExecutionException, InterruptedException {
        ProductDetailVO productDetailVO = goodsService.getProductDetailVO(spuId);
        return R.ok().put("data", productDetailVO);
    }

}
