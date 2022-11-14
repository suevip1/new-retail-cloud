package com.zhihao.newretail.product.dao;

import com.zhihao.newretail.product.pojo.Slide;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SlideMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Slide record);

    int insertSelective(Slide record);

    Slide selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Slide record);

    int updateByPrimaryKey(Slide record);

    int countSlide(Integer isDelete);

    List<Slide> selectListByAll();

    List<Slide> selectListByRecord(@Param("id") Integer id, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

}
