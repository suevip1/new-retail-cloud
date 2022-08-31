package com.zhihao.newretail.product.dao;

import com.zhihao.newretail.product.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    int countCategory();

    /*
    * 获取所有商品分类
    * */
    List<Category> selectListByAll();

    List<Category> selectListByPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

}
