package com.zhihao.newretail.product.dao;

import com.zhihao.newretail.product.pojo.SkuStockLock;

public interface SkuStockLockMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SkuStockLock record);

    int insertSelective(SkuStockLock record);

    SkuStockLock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SkuStockLock record);

    int updateByPrimaryKey(SkuStockLock record);

}
