package com.zhihao.newretail.rbac.service.impl;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;
import com.zhihao.newretail.api.product.feign.CategoryFeignService;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.rbac.annotation.RequiresPermission;
import com.zhihao.newretail.rbac.consts.AuthorizationConst;
import com.zhihao.newretail.rbac.service.SysCategoryService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysCategoryServiceImpl implements SysCategoryService {

    @Autowired
    private CategoryFeignService categoryFeignService;

    @Override
    @RequiresPermission(scope = AuthorizationConst.ADMIN)
    public void addCategory(CategoryAddApiDTO categoryAddApiDTO) {
        categoryFeignService.addCategory(categoryAddApiDTO);
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.ADMIN)
    public void updateCategory(Integer categoryId, CategoryUpdateApiDTO categoryUpdateApiDTO) {
        if (categoryId == null) {
            throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "请选择修改的分类");
        }
        categoryFeignService.updateCategory(categoryId, categoryUpdateApiDTO);
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.ROOT)
    public void deleteCategory(Integer categoryId) {
        if (categoryId == null) {
            throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "请选择删除的分类");
        }
        categoryFeignService.deleteCategory(categoryId);
    }

}
