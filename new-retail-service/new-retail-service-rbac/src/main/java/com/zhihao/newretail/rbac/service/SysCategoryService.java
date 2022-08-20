package com.zhihao.newretail.rbac.service;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.CategoryApiVO;

import java.util.List;

public interface SysCategoryService {

    /*
    * 商品分类列表
    * */
    List<CategoryApiVO> listCategoryApiVOS();

    /*
    * 商品分类信息
    * */
    CategoryApiVO getCategoryApiVO(Integer categoryId);

    /*
    * 新增商品分类
    * */
    void addCategory(CategoryAddApiDTO categoryAddApiDTO);

    /*
    * 修改商品分类
    * */
    void updateCategory(Integer categoryId, CategoryUpdateApiDTO categoryUpdateApiDTO);

    /*
    * 删除商品分类
    * */
    void deleteCategory(Integer categoryId);

}
