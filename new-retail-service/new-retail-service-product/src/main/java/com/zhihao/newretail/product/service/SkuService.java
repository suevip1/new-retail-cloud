package com.zhihao.newretail.product.service;

import com.zhihao.newretail.product.pojo.Sku;

import java.util.List;

public interface SkuService {

    /*
    * spuId获取sku
    * */
    Sku getSku(Integer spuId);

    /*
    * spuId获取skuList
    * */
    List<Sku> listSkuS(Integer spuId);

}
