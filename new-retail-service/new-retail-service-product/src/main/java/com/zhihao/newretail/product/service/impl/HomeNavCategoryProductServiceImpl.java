package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.product.consts.ProductCacheConst;
import com.zhihao.newretail.product.factory.HomeProductResourcesFactory;
import com.zhihao.newretail.product.pojo.vo.HomeProductVO;
import com.zhihao.newretail.product.service.CategoryService;
import com.zhihao.newretail.product.service.HomeProductResourcesService;
import com.zhihao.newretail.product.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service(ProductCacheConst.HOME_NAV_CATEGORY_PRODUCT_LIST)
public class HomeNavCategoryProductServiceImpl implements HomeProductResourcesService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SpuService spuService;

    @Override
    public List<HomeProductVO> listHomeProductVOS() {
        return HomeProductResourcesFactory.getResources(categoryService, spuService, 6).stream().limit(7).collect(Collectors.toList());
    }

}
