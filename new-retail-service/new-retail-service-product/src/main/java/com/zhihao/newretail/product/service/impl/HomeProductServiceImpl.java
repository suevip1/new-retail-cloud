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

@Service(ProductCacheConst.HOME_PRODUCT_LIST)
public class HomeProductServiceImpl implements HomeProductResourcesService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SpuService spuService;

    @Override
    public List<HomeProductVO> listHomeProductVOS() {
        return HomeProductResourcesFactory.getResources(categoryService, spuService, 10);
    }

}