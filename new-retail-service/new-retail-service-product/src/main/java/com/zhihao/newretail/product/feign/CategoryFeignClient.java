package com.zhihao.newretail.product.feign;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.CategoryApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.product.service.CategoryService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feign")
public class CategoryFeignClient {

    @Autowired
    private CategoryService categoryService;

    @RequiresLogin
    @GetMapping("/category/list")
    public PageUtil<CategoryApiVO> listCategoryApiVOS(@RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        PageUtil<CategoryApiVO> pageData = categoryService.listCategoryApiVOS(pageNum, pageSize);
        UserLoginContext.sysClean();
        return pageData;
    }

    @RequiresLogin
    @GetMapping("/category/{categoryId}")
    public CategoryApiVO getCategoryApiVO(@PathVariable Integer categoryId) {
        CategoryApiVO categoryApiVO = categoryService.getCategoryApiVO(categoryId);
        UserLoginContext.sysClean();
        return categoryApiVO;
    }

    @RequiresLogin
    @PostMapping("/category")
    public Integer addCategory(@RequestBody CategoryAddApiDTO categoryAddApiDTO) {
        int insertRow = categoryService.insertCategory(categoryAddApiDTO);
        UserLoginContext.sysClean();
        return insertRow;
    }

    @RequiresLogin
    @PutMapping("/category/{categoryId}")
    public Integer updateCategory(@PathVariable Integer categoryId, @RequestBody CategoryUpdateApiDTO categoryUpdateApiDTO) {
        int updateRow = categoryService.updateCategory(categoryId, categoryUpdateApiDTO);
        UserLoginContext.sysClean();
        return updateRow;
    }

    @RequiresLogin
    @DeleteMapping("/category/{categoryId}")
    public Integer deleteCategory(@PathVariable Integer categoryId) {
        int deleteRow = categoryService.deleteCategory(categoryId);
        UserLoginContext.sysClean();
        return deleteRow;
    }

}
