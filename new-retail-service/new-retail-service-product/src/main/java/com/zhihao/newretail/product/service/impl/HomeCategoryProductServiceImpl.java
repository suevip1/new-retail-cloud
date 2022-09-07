package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.product.consts.ProductCacheConst;
import com.zhihao.newretail.product.factory.HomeProductResourcesFactory;
import com.zhihao.newretail.product.pojo.Category;
import com.zhihao.newretail.product.pojo.Spu;
import com.zhihao.newretail.product.pojo.vo.HomeProductVO;
import com.zhihao.newretail.product.pojo.vo.ProductVO;
import com.zhihao.newretail.product.service.CategoryService;
import com.zhihao.newretail.product.service.HomeProductResourcesService;
import com.zhihao.newretail.product.service.SpuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service(ProductCacheConst.HOME_CATEGORY_PRODUCT_LIST)
public class HomeCategoryProductServiceImpl implements HomeProductResourcesService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SpuService spuService;

    @Override
    public List<HomeProductVO> listHomeProductVOS() {
        List<Category> categoryList = categoryService.listCategories();
        Set<Integer> categoryIdSet = categoryList.stream().map(Category::getId).collect(Collectors.toSet());
        List<Spu> spuList = spuService.listSpuSByCategoryIdSet(categoryIdSet);
        return categoryList.stream().map(category -> {
            HomeProductVO homeProductVO = new HomeProductVO();
            BeanUtils.copyProperties(category, homeProductVO);
            List<ProductVO> productVOList = HomeProductResourcesFactory.spuList2ProductVOList(spuList, category);
            homeProductVO.setProductVOList(productVOList);
            return homeProductVO;
        }).collect(Collectors.toList());
    }

}
