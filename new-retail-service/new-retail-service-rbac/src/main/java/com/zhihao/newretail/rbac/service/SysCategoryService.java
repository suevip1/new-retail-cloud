package com.zhihao.newretail.rbac.service;

import com.zhihao.newretail.api.product.dto.CategoryDTO;

public interface SysCategoryService {

    /*
    * 新增商品分类
    * */
    void addCategory(CategoryDTO categoryDTO);

}
