package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.core.enums.DeleteEnum;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.product.dao.CategoryMapper;
import com.zhihao.newretail.product.enums.CategoryEnum;
import com.zhihao.newretail.product.pojo.Category;
import com.zhihao.newretail.product.pojo.vo.CategoryVO;
import com.zhihao.newretail.product.service.CategoryService;
import org.apache.http.HttpStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryVO> listCategoryVOs() {
        List<Category> categoryList = categoryMapper.selectListByAll();

        if (CollectionUtils.isEmpty(categoryList))
            throw new ServiceException(HttpStatus.SC_NO_CONTENT, "暂无数据");

        List<CategoryVO> categoryVOList = categoryList.stream()
                .filter(category -> DeleteEnum.NOT_DELETE.getCode().equals(category.getIsDelete()))
                .filter(category -> CategoryEnum.ROOT_NODE.getCode().equals(category.getParentId()))
                .map(this::Category2CategoryVO).collect(Collectors.toList());
        findSubCategoryVOList(categoryList, categoryVOList);
        return categoryVOList;
    }

    private void findSubCategoryVOList(List<Category> categoryList, List<CategoryVO> categoryVOList) {
        categoryVOList.forEach(categoryVO -> {
            List<CategoryVO> subCategoryVOList = new ArrayList<>();
            categoryList.stream()
                    .filter(category -> DeleteEnum.NOT_DELETE.getCode().equals(category.getIsDelete()))
                    .filter(category -> categoryVO.getId().equals(category.getParentId()))
                    .collect(Collectors.toList())
                    .forEach(category -> {
                        CategoryVO subCategoryVO = Category2CategoryVO(category);
                        subCategoryVOList.add(subCategoryVO);
                        findSubCategoryVOList(categoryList, subCategoryVOList);
                    });
            categoryVO.setCategoryVOList(subCategoryVOList);
        });
    }

    private CategoryVO Category2CategoryVO(Category category) {
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(category, categoryVO);
        return categoryVO;
    }

}