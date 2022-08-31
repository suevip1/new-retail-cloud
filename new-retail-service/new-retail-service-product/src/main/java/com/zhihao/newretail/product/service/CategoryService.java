package com.zhihao.newretail.product.service;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.CategoryApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.product.pojo.Category;
import com.zhihao.newretail.product.pojo.vo.CategoryVO;

import java.util.List;

public interface CategoryService {

    /*
    * 商品分类列表
    * */
    List<CategoryVO> listCategoryVOS();

    /*
    * 商品分类列表
    * */
    List<Category> listCategories();

    /*
    * 商品分类列表(feign)
    * */
    PageUtil<CategoryApiVO> listCategoryApiVOS(Integer pageNum, Integer pageSize);

    /*
    * 商品分类信息
    * */
    CategoryApiVO getCategoryApiVO(Integer categoryId);

    /*
    * 新增商品分类
    * */
    int insertCategory(CategoryAddApiDTO categoryAddApiDTO);

    /*
    * 修改商品分类
    * */
    int updateCategory(Integer categoryId, CategoryUpdateApiDTO categoryUpdateApiDTO);

    /*
    * 删除商品分类
    * */
    int deleteCategory(Integer categoryId);

}
