package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.CategoryApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.product.dao.CategoryMapper;
import com.zhihao.newretail.product.enums.CategoryEnum;
import com.zhihao.newretail.product.pojo.Category;
import com.zhihao.newretail.product.vo.CategoryVO;
import com.zhihao.newretail.product.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    public List<CategoryVO> listCategoryVOS() {
        List<Category> categoryList = categoryMapper.selectListByAll();
        List<CategoryVO> categoryVOList = categoryList.stream()
                .filter(category -> CategoryEnum.ROOT_NODE.getCode().equals(category.getParentId()))
                .map(this::Category2CategoryVO).collect(Collectors.toList());
        findSubCategoryVOList(categoryList, categoryVOList);
        return categoryVOList;
    }

    @Override
    public List<Category> listCategories() {
        List<Category> categoryList = categoryMapper.selectListByAll();
        return categoryList.stream()
                .filter(category -> CategoryEnum.ROOT_NODE.getCode().equals(category.getParentId()))
                .collect(Collectors.toList());
    }

    @Override
    public PageUtil<CategoryApiVO> listCategoryApiVOS(Integer pageNum, Integer pageSize) {
        PageUtil<CategoryApiVO> pageUtil = new PageUtil<>();
        CompletableFuture<Void> totalFuture = CompletableFuture.runAsync(() -> {
            int countCategory = categoryMapper.countCategory();
            pageUtil.setPageNum(pageNum);
            pageUtil.setPageSize(pageSize);
            pageUtil.setTotal((long) countCategory);
        }, executor);
        CompletableFuture<Void> listFuture = CompletableFuture.runAsync(() -> {
            List<Category> categoryList = categoryMapper.selectListByPage(pageNum, pageSize);
            if (!CollectionUtils.isEmpty(categoryList)) {
                List<CategoryApiVO> categoryApiVOList = categoryList.stream().map(this::category2CategoryApiVO).collect(Collectors.toList());
                pageUtil.setList(categoryApiVOList);
            }
        }, executor);
        CompletableFuture.allOf(totalFuture, listFuture).join();
        return pageUtil;
    }

    @Override
    public CategoryApiVO getCategoryApiVO(Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        return category2CategoryApiVO(category);
    }

    @Override
    public int insertCategory(CategoryAddApiDTO categoryAddApiDTO) {
        Category category = new Category();
        category.setName(categoryAddApiDTO.getName());
        category.setParentId(categoryAddApiDTO.getParentId());
        return categoryMapper.insertSelective(category);
    }

    @Override
    public int updateCategory(Integer categoryId, CategoryUpdateApiDTO categoryUpdateApiDTO) {
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryUpdateApiDTO.getName());
        category.setParentId(categoryUpdateApiDTO.getParentId());
        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public int deleteCategory(Integer categoryId) {
        return categoryMapper.deleteByPrimaryKey(categoryId);
    }

    private void findSubCategoryVOList(List<Category> categoryList, List<CategoryVO> categoryVOList) {
        categoryVOList.forEach(categoryVO -> {
            List<CategoryVO> subCategoryVOList = new ArrayList<>();
            categoryList.stream()
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

    private CategoryApiVO category2CategoryApiVO(Category category) {
        CategoryApiVO categoryApiVO = new CategoryApiVO();
        BeanUtils.copyProperties(category, categoryApiVO);
        return categoryApiVO;
    }

}
