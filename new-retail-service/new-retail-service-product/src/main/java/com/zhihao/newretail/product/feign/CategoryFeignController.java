package com.zhihao.newretail.product.feign;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;
import com.zhihao.newretail.api.product.feign.CategoryFeignService;
import com.zhihao.newretail.product.service.CategoryService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryFeignController implements CategoryFeignService {

    @Autowired
    private CategoryService categoryService;

    @Override
    @RequiresLogin
    public void addCategory(CategoryAddApiDTO categoryAddApiDTO) {
        categoryService.insertCategory(categoryAddApiDTO);
        UserLoginContext.sysClean();
    }

    @Override
    @RequiresLogin
    public void updateCategory(Integer categoryId, CategoryUpdateApiDTO categoryUpdateApiDTO) {
        categoryService.updateCategory(categoryId, categoryUpdateApiDTO);
        UserLoginContext.sysClean();
    }

}
