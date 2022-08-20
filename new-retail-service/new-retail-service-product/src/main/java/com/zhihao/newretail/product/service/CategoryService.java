package com.zhihao.newretail.product.service;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.CategoryApiVO;
import com.zhihao.newretail.product.pojo.vo.CategoryVO;

import java.util.List;

public interface CategoryService {

    /*
    * 商品分类列表
    * */
    List<CategoryVO> listCategoryVOS();

    /*
    * 商品分类列表(feign)
    * */
    List<CategoryApiVO> listCategoryApiVOS();

    /*
    * 商品分类信息
    * */
    CategoryApiVO getCategoryApiVO(Integer categoryId);

    /*
    * 新增商品分类
    * */
    void insertCategory(CategoryAddApiDTO categoryAddApiDTO);

    /*
    * 修改商品分类
    * */
    void updateCategory(Integer categoryId, CategoryUpdateApiDTO categoryUpdateApiDTO);

    /*
    * 删除商品分类
    * */
    void deleteCategory(Integer categoryId);

}
