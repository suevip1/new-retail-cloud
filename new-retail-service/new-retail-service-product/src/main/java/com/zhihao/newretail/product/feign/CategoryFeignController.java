package com.zhihao.newretail.product.feign;

import com.zhihao.newretail.api.product.dto.CategoryDTO;
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

    @RequiresLogin
    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        categoryService.insertCategory(categoryDTO);
        UserLoginContext.sysClean();
    }

}
