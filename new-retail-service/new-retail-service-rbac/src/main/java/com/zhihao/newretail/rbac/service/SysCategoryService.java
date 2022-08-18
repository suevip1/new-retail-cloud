package com.zhihao.newretail.rbac.service;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;

public interface SysCategoryService {

    /*
    * 新增商品分类
    * */
    void addCategory(CategoryAddApiDTO categoryAddApiDTO);

    /*
    * 修改商品分类
    * */
    void updateCategory(Integer categoryId, CategoryUpdateApiDTO categoryUpdateApiDTO);

}
