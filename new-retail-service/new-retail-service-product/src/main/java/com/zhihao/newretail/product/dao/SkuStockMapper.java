package com.zhihao.newretail.product.dao;

import com.zhihao.newretail.product.pojo.SkuStock;

public interface SkuStockMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SkuStock record);

    int insertSelective(SkuStock record);

    SkuStock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SkuStock record);

    int updateByPrimaryKey(SkuStock record);

    SkuStock selectBySkuId(Integer skuId);

}
