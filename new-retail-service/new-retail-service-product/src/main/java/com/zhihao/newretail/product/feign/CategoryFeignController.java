package com.zhihao.newretail.product.feign;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;
import com.zhihao.newretail.api.product.feign.CategoryFeignService;
import com.zhihao.newretail.api.product.vo.CategoryApiVO;
import com.zhihao.newretail.product.service.CategoryService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryFeignController implements CategoryFeignService {

    @Autowired
    private CategoryService categoryService;

    @Override
    @RequiresLogin
    public List<CategoryApiVO> listCategoryApiVOS() {
        List<CategoryApiVO> categoryApiVOList = categoryService.listCategoryApiVOS();
        UserLoginContext.sysClean();
        return categoryApiVOList;
    }

    @Override
    @RequiresLogin
    public CategoryApiVO getCategoryApiVO(Integer categoryId) {
        CategoryApiVO categoryApiVO = categoryService.getCategoryApiVO(categoryId);
        UserLoginContext.sysClean();
        return categoryApiVO;
    }

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

    @Override
    @RequiresLogin
    public void deleteCategory(Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        UserLoginContext.sysClean();
    }

}
