package com.zhihao.newretail.product.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.product.pojo.vo.ProductDetailVO;
import com.zhihao.newretail.product.pojo.vo.ProductVO;
import com.zhihao.newretail.product.service.ProductService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/detail/{spuId}")
    public R productDetail(@PathVariable Integer spuId) throws ExecutionException, InterruptedException {
        ProductDetailVO productDetailVO = productService.getProductDetailVO(spuId);
        if (!ObjectUtils.isEmpty(productDetailVO)) {
            return R.ok().put("data", productDetailVO);
        } else {
            return R.error(HttpStatus.SC_NOT_FOUND, "商品不存在").put("data", productDetailVO);
        }
    }

    @GetMapping("/list/{categoryId}")
    public R productList(@PathVariable Integer categoryId) {
        List<ProductVO> productVOList = productService.listProductVOS(categoryId);
        if (!CollectionUtils.isEmpty(productVOList)) {
            return R.ok().put("data", productVOList);
        } else {
            return R.error(HttpStatus.SC_NO_CONTENT, "暂无数据").put("data", productVOList);
        }
    }

}
