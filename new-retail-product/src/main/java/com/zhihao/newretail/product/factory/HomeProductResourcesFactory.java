package com.zhihao.newretail.product.factory;

import com.zhihao.newretail.core.util.BeanCopyUtil;
import com.zhihao.newretail.product.pojo.Category;
import com.zhihao.newretail.product.pojo.Sku;
import com.zhihao.newretail.product.pojo.Spu;
import com.zhihao.newretail.product.vo.HomeProductVO;
import com.zhihao.newretail.product.vo.ProductVO;
import com.zhihao.newretail.product.service.CategoryService;
import com.zhihao.newretail.product.service.HomeProductResourcesService;
import com.zhihao.newretail.product.service.SpuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class HomeProductResourcesFactory {

    @Autowired
    private final Map<String, HomeProductResourcesService> map = new ConcurrentHashMap<>();

    public HomeProductResourcesService getResources(String type) {
        return map.get(type);
    }

    public static List<HomeProductVO> getResources(CategoryService categoryService, SpuService spuService, Integer slice) {
        List<Category> categoryList = categoryService.listCategories();
        Set<Integer> categoryIdSet = categoryList.stream().map(Category::getId).collect(Collectors.toSet());
        List<Spu> spuList = spuService.listSpuSByCategoryIdSet(categoryIdSet);
        List<HomeProductVO> homeProductVOList = new ArrayList<>();
        for (Category category : categoryList) {
            List<ProductVO> productVOList = spuList2ProductVOList(spuList, category).stream().limit(slice).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(productVOList)) {
                HomeProductVO homeProductVO = new HomeProductVO();
                BeanUtils.copyProperties(category, homeProductVO);
                homeProductVO.setProductVOList(productVOList);
                homeProductVOList.add(homeProductVO);
            }
        }
        return homeProductVOList;
    }

    public static List<ProductVO> spuList2ProductVOList(List<Spu> spuList, Category category) {
        return spuList.stream()
                .filter(spu -> category.getId().equals(spu.getCategoryId()))
                .map(spu -> {
                    ProductVO productVO = BeanCopyUtil.source2Target(spu, ProductVO.class);
                    if (!ObjectUtils.isEmpty(productVO)) {
                        productVO.setShowImage(spu.getSpuInfo().getShowImage());
                        List<Sku> skuList = spu.getSkuList().stream().sorted(Comparator.comparing(Sku::getPrice)).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(skuList)) {
                            productVO.setPrice(skuList.get(0).getPrice());
                        }
                    }
                    return productVO;
                }).collect(Collectors.toList());
    }

}
