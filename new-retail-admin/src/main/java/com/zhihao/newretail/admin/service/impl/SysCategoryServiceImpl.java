package com.zhihao.newretail.admin.service.impl;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;
import com.zhihao.newretail.api.product.feign.CategoryFeignService;
import com.zhihao.newretail.api.product.vo.CategoryApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.admin.annotation.RequiresPermission;
import com.zhihao.newretail.admin.consts.AuthorizationConst;
import com.zhihao.newretail.admin.service.SysCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysCategoryServiceImpl implements SysCategoryService {

    @Autowired
    private CategoryFeignService categoryFeignService;

    @Override
    @RequiresPermission(scope = AuthorizationConst.COMMON)
    public PageUtil<CategoryApiVO> listCategoryApiVOS(Integer pageNum, Integer pageSize) {
        return categoryFeignService.listCategoryApiVOS(pageNum, pageSize);
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.COMMON)
    public CategoryApiVO getCategoryApiVO(Integer categoryId) {
        return categoryFeignService.getCategoryApiVO(categoryId);
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.ADMIN)
    public Integer addCategory(CategoryAddApiDTO categoryAddApiDTO) {
        return categoryFeignService.addCategory(categoryAddApiDTO);
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.ADMIN)
    public Integer updateCategory(Integer categoryId, CategoryUpdateApiDTO categoryUpdateApiDTO) {
        return categoryFeignService.updateCategory(categoryId, categoryUpdateApiDTO);
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.ROOT)
    public Integer deleteCategory(Integer categoryId) {
        return categoryFeignService.deleteCategory(categoryId);
    }

}
