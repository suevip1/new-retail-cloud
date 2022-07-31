package com.zhihao.newretail.product.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.product.pojo.vo.CategoryVO;
import com.zhihao.newretail.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category/list")
    public R categories() {
        List<CategoryVO> categoryVOList = categoryService.listCategoryVOs();
        return R.ok().put("data", categoryVOList);
    }

}
