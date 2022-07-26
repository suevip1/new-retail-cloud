package com.zhihao.newretail.product.dao;

import com.zhihao.newretail.product.pojo.Sku;

import java.util.List;

public interface SkuMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Sku record);

    int insertSelective(Sku record);

    Sku selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Sku record);

    int updateByPrimaryKey(Sku record);

    /*
    * spuId获取规格列表
    * */
    List<Sku> selectListBySpuId(Integer spuId);

}
