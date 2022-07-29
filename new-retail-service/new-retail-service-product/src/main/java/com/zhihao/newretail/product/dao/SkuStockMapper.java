package com.zhihao.newretail.product.dao;

import com.zhihao.newretail.product.pojo.SkuStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface SkuStockMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SkuStock record);

    int insertSelective(SkuStock record);

    SkuStock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SkuStock record);

    int updateByPrimaryKey(SkuStock record);

    SkuStock selectBySkuId(Integer skuId);

    /*
    * 批量查询
    * */
    List<SkuStock> selectListBySkuIdSet(@Param("SkuIdSet") Set<Integer> SkuIdSet);

}
