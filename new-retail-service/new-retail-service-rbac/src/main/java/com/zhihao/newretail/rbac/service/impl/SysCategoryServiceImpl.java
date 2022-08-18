package com.zhihao.newretail.rbac.service.impl;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;
import com.zhihao.newretail.api.product.feign.CategoryFeignService;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.rbac.annotation.RequiresPermission;
import com.zhihao.newretail.rbac.consts.AuthorizationConst;
import com.zhihao.newretail.rbac.service.SysCategoryService;
import org.apache.commons.lang3.StringUtils;
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
        String name = categoryAddApiDTO.getName();
        Integer parentId = categoryAddApiDTO.getParentId();
        if (StringUtils.isEmpty(name)) {
            throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "分类名称不能为空");
        }
        if (parentId == null) {
            throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "所属分类不能为空");
        }
        categoryFeignService.addCategory(categoryAddApiDTO);
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.ADMIN)
    public void updateCategory(Integer categoryId, CategoryUpdateApiDTO categoryUpdateApiDTO) {
        String name = categoryUpdateApiDTO.getName();
        Integer parentId = categoryUpdateApiDTO.getParentId();
        if (categoryId == null) {
            throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "请选择修改的分类");
        }
        if (StringUtils.isEmpty(name)) {
            throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "分类名称不能为空");
        }
        if (parentId == null) {
            throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "所属分类不能为空");
        }
        categoryFeignService.updateCategory(categoryId, categoryUpdateApiDTO);
    }

}
