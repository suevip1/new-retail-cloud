package com.zhihao.newretail.rbac.service.impl;

import com.zhihao.newretail.api.product.dto.CategoryDTO;
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
    public void addCategory(CategoryDTO categoryDTO) {
        String name = categoryDTO.getName();
        Integer parentId = categoryDTO.getParentId();
        if (StringUtils.isEmpty(name)) {
            throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "分类名称不能为空");
        }
        if (parentId == null) {
            throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "所属分类不能为空");
        }
        categoryFeignService.addCategory(categoryDTO);
    }

}
