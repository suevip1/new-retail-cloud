package com.zhihao.newretail.product.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.product.vo.CategoryVO;
import com.zhihao.newretail.product.service.CategoryService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category/list")
    public R categories() {
        List<CategoryVO> categoryVOList = categoryService.listCategoryVOS();
        if (!CollectionUtils.isEmpty(categoryVOList)) {
            return R.ok().put("data", categoryVOList);
        } else {
            return R.error(HttpStatus.SC_NO_CONTENT, "暂无数据").put("data", categoryVOList);
        }
    }

}
