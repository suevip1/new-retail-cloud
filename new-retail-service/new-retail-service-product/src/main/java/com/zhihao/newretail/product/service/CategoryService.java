package com.zhihao.newretail.product.service;

import com.zhihao.newretail.product.pojo.vo.CategoryVO;

import java.util.List;

public interface CategoryService {

    /*
    * 商品分类列表
    * */
    List<CategoryVO> listCategoryVOs();

}
