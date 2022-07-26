package com.zhihao.newretail.product.service;

import com.zhihao.newretail.product.pojo.vo.ProductDetailVO;

import java.util.concurrent.ExecutionException;

public interface ProductService {

    /*
    * 获取商品详情
    * */
    ProductDetailVO getProductDetailVO(Integer spuId) throws ExecutionException, InterruptedException;

}
