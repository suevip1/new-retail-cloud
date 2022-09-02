package com.zhihao.newretail.product.controller;

import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.product.pojo.vo.ProductDetailVO;
import com.zhihao.newretail.product.pojo.vo.ProductVO;
import com.zhihao.newretail.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/detail/{spuId}")
    public R productDetail(@PathVariable Integer spuId) {
        ProductDetailVO productDetailVO = productService.getProductDetailVO(spuId);
        return R.ok().put("data", productDetailVO);
    }

    @GetMapping("/list/{categoryId}")
    public R productList(@PathVariable Integer categoryId,
                         @RequestParam(defaultValue = "1") Integer pageNum,
                         @RequestParam(defaultValue = "20") Integer pageSize) {
        if (pageNum == 0 || pageSize == 0) {
            throw new ServiceException("分页参数不能为0");
        }
        PageUtil<ProductVO> pageData = productService.listProductVOS(categoryId, pageNum, pageSize);
        return R.ok().put("data", pageData);
    }

}
