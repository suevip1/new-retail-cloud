package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.product.consts.ProductCacheConst;
import com.zhihao.newretail.product.pojo.Category;
import com.zhihao.newretail.product.pojo.Sku;
import com.zhihao.newretail.product.pojo.Spu;
import com.zhihao.newretail.product.pojo.vo.HomeProductVO;
import com.zhihao.newretail.product.pojo.vo.ProductVO;
import com.zhihao.newretail.product.service.CategoryService;
import com.zhihao.newretail.product.service.HomeProductResourcesService;
import com.zhihao.newretail.product.service.SpuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service(ProductCacheConst.HOME_PRODUCT_LIST)
public class HomeProductServiceImpl implements HomeProductResourcesService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SpuService spuService;

    @Override
    public List<HomeProductVO> listHomeProductVOS() {
        List<Category> categoryList = categoryService.listCategories();
        Set<Integer> categoryIdSet = categoryList.stream().map(Category::getId).collect(Collectors.toSet());
        List<Spu> spuList = spuService.listSpuSByCategoryIdSet(categoryIdSet);
        List<HomeProductVO> homeProductVOList = new ArrayList<>();
        for (Category category : categoryList) {
            List<ProductVO> productVOList = spuList.stream()
                    .filter(spu -> category.getId().equals(spu.getCategoryId())).map(this::spu2ProductVO)
                    .limit(10)
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(productVOList)) {
                HomeProductVO homeProductVO = new HomeProductVO();
                BeanUtils.copyProperties(category, homeProductVO);
                homeProductVO.setProductVOList(productVOList);
                homeProductVOList.add(homeProductVO);
            }
        }
        return homeProductVOList;
    }

    private ProductVO spu2ProductVO(Spu spu) {
        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(spu, productVO);
        productVO.setShowImage(spu.getSpuInfo().getShowImage());
        List<Sku> skuList = spu.getSkuList().stream().sorted(Comparator.comparing(Sku::getPrice)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(skuList)) {
            productVO.setPrice(skuList.get(0).getPrice());
        }
        return productVO;
    }

}
