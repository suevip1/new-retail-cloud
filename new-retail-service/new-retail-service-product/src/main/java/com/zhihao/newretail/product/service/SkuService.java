package com.zhihao.newretail.product.service;

import com.zhihao.newretail.api.product.dto.SkuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SkuUpdateApiDTO;
import com.zhihao.newretail.product.pojo.Sku;

import java.util.List;
import java.util.Set;

public interface SkuService {

    /*
    * 新增商品规格
    * */
    int insertSku(SkuAddApiDTO skuAddApiDTO);

    /*
    * 修改商品规格
    * */
    int updateSku(Integer skuId, SkuUpdateApiDTO skuUpdateApiDTO);

    /*
    * 删除商品规格
    * */
    int deleteSku(Integer skuId);

    /*
    * 获取商品规格
    * */
    Sku getSku(Integer skuId);

    /*
    * spuId获取skuList
    * */
    List<Sku> listSkuS(Integer spuId);

    /*
    * 批量获取skuList
    * */
    List<Sku> listSkuS(Set<Integer> skuIdSet);

}
