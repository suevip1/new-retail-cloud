package com.zhihao.newretail.product.dao;

import com.zhihao.newretail.product.pojo.SpecParamValue;

public interface SpecParamValueMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SpecParamValue record);

    int insertSelective(SpecParamValue record);

    SpecParamValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SpecParamValue record);

    int updateByPrimaryKey(SpecParamValue record);

}
