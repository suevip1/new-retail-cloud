package com.zhihao.newretail.product.dao;

import com.zhihao.newretail.product.pojo.SpecParamKey;

import java.util.List;

public interface SpecParamKeyMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SpecParamKey record);

    int insertSelective(SpecParamKey record);

    SpecParamKey selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SpecParamKey record);

    int updateByPrimaryKey(SpecParamKey record);

    List<SpecParamKey> selectListByCategoryId(Integer categoryId);

}
