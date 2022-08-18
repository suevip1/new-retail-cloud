package com.zhihao.newretail.product.service;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;
import com.zhihao.newretail.product.pojo.vo.CategoryVO;

import java.util.List;

public interface CategoryService {

    /*
    * 商品分类列表
    * */
    List<CategoryVO> listCategoryVOs();

    /*
    * 新增商品分类
    * */
    void insertCategory(CategoryAddApiDTO categoryAddApiDTO);

    /*
    * 修改商品分类
    * */
    void updateCategory(Integer categoryId, CategoryUpdateApiDTO categoryUpdateApiDTO);

}
