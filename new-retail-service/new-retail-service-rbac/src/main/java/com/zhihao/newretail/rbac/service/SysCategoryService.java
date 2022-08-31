package com.zhihao.newretail.rbac.service;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.CategoryApiVO;
import com.zhihao.newretail.core.util.PageUtil;

public interface SysCategoryService {

    /*
    * 商品分类列表
    * */
    PageUtil<CategoryApiVO> listCategoryApiVOS(Integer pageNum, Integer pageSize);

    /*
    * 商品分类信息
    * */
    CategoryApiVO getCategoryApiVO(Integer categoryId);

    /*
    * 新增商品分类
    * */
    Integer addCategory(CategoryAddApiDTO categoryAddApiDTO);

    /*
    * 修改商品分类
    * */
    Integer updateCategory(Integer categoryId, CategoryUpdateApiDTO categoryUpdateApiDTO);

    /*
    * 删除商品分类
    * */
    Integer deleteCategory(Integer categoryId);

}
