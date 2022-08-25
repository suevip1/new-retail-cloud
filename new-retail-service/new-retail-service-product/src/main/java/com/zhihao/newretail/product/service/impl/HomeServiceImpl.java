package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.product.pojo.Category;
import com.zhihao.newretail.product.pojo.Spu;
import com.zhihao.newretail.product.pojo.vo.HomeProductVO;
import com.zhihao.newretail.product.pojo.vo.ProductVO;
import com.zhihao.newretail.product.service.CategoryService;
import com.zhihao.newretail.product.service.HomeService;
import com.zhihao.newretail.product.service.SpuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HomeServiceImpl implements HomeService {

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
            List<ProductVO> productVOList = spuList.stream()
                    .filter(spu -> category.getId().equals(spu.getCategoryId()))
                    .map(this::spu2ProductVO)
                    .collect(Collectors.toList());
            homeProductVO.setProductVOList(productVOList);
            return homeProductVO;
        }).collect(Collectors.toList());
    }

    private ProductVO spu2ProductVO(Spu spu) {
        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(spu, productVO);
        productVO.setShowImage(spu.getSpuInfo().getShowImage());
        return productVO;
    }

}
