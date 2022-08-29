package com.zhihao.newretail.api.product.fallback;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;
import com.zhihao.newretail.api.product.feign.CategoryFeignService;
import com.zhihao.newretail.api.product.vo.CategoryApiVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryFeignFallback implements CategoryFeignService {

    @Override
    public List<CategoryApiVO> listCategoryApiVOS() {
        return null;
    }

    @Override
    public CategoryApiVO getCategoryApiVO(Integer categoryId) {
        return null;
    }

    @Override
    public Integer addCategory(CategoryAddApiDTO categoryAddApiDTO) {
        return null;
    }

    @Override
    public Integer updateCategory(Integer categoryId, CategoryUpdateApiDTO categoryUpdateApiDTO) {
        return null;
    }

    @Override
    public Integer deleteCategory(Integer categoryId) {
        return null;
    }

}
