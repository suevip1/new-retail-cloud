package com.zhihao.newretail.product.dao;

import com.zhihao.newretail.product.pojo.SpecParamKey;

public interface SpecParamKeyMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SpecParamKey record);

    int insertSelective(SpecParamKey record);

    SpecParamKey selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SpecParamKey record);

    int updateByPrimaryKey(SpecParamKey record);

}
