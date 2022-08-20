package com.zhihao.newretail.product.service;

import com.zhihao.newretail.api.product.vo.SkuApiVO;
import com.zhihao.newretail.product.pojo.vo.GoodsVO;
import com.zhihao.newretail.product.pojo.vo.ProductDetailVO;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface GoodsService {

    /*
    * 获取商品详情
    * */
    ProductDetailVO getProductDetailVO(Integer spuId) throws ExecutionException, InterruptedException;

    /*
    * 批量获取sku
    * */
    List<SkuApiVO> listSkuApiVOs(Set<Integer> skuIdSet);

    /*
    * 获取sku
    * */
    SkuApiVO getSkuApiVO(Integer skuId);

    /*
    * 获取商品
    * */
    List<GoodsVO> listGoodsVOS(Integer categoryId);

}
