package com.zhihao.newretail.product.dao;

import com.zhihao.newretail.product.pojo.Spu;

public interface SpuMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Spu record);

    int insertSelective(Spu record);

    Spu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Spu record);

    int updateByPrimaryKey(Spu record);

}
