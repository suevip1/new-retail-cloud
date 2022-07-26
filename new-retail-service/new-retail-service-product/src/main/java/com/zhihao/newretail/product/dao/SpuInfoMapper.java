package com.zhihao.newretail.product.dao;

import com.zhihao.newretail.product.pojo.SpuInfo;

public interface SpuInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SpuInfo record);

    int insertSelective(SpuInfo record);

    SpuInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SpuInfo record);

    int updateByPrimaryKey(SpuInfo record);

    /*
    * spuId获取商品信息
    * */
    SpuInfo selectBySpuId(Integer spuId);

}
