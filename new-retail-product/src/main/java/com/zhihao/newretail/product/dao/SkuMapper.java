package com.zhihao.newretail.product.dao;

import com.zhihao.newretail.product.pojo.Sku;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface SkuMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Sku record);

    int insertSelective(Sku record);

    Sku selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Sku record);

    int updateByPrimaryKey(Sku record);

    Sku selectBySpuId(Integer spuId);

    /*
    * spuId获取规格列表
    * */
    List<Sku> selectListBySpuId(Integer spuId);

    /*
    * 批量查询
    * */
    List<Sku> selectListByIdSet(@Param("idSet") Set<Integer> idSet);

}
