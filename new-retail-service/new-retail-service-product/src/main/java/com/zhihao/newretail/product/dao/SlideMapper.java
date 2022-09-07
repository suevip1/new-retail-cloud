package com.zhihao.newretail.product.dao;

import com.zhihao.newretail.product.pojo.Slide;

import java.util.List;

public interface SlideMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Slide record);

    int insertSelective(Slide record);

    Slide selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Slide record);

    int updateByPrimaryKey(Slide record);

    List<Slide> selectListByAll();

}
