package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.core.util.GsonUtil;
import com.zhihao.newretail.product.pojo.Category;
import com.zhihao.newretail.product.pojo.Spu;
import com.zhihao.newretail.product.pojo.vo.HomeProductVO;
import com.zhihao.newretail.product.pojo.vo.ProductVO;
import com.zhihao.newretail.product.service.CategoryService;
import com.zhihao.newretail.product.service.HomeService;
import com.zhihao.newretail.product.service.SpuService;
import com.zhihao.newretail.redis.util.MyRedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.zhihao.newretail.product.consts.ProductCacheConst.*;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SpuService spuService;

    @Autowired
    private MyRedisUtil redisUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public List<HomeProductVO> listHomeProductVOS() {
        RLock lock = redissonClient.getLock(HOME_PRODUCT_LIST_LOCK);
        lock.lock();
        try {
            String str = (String) redisUtil.getObject(HOME_PRODUCT_LIST);
            if (StringUtils.isEmpty(str)) {
                List<HomeProductVO> homeProductVOList = getHomeProductVOResources();
                /* 返回结果为空，key存null值解决缓存穿透 */
                if (CollectionUtils.isEmpty(homeProductVOList)) {
                    redisUtil.setObject(HOME_PRODUCT_LIST, PRESENT, 43200L);
                }
                redisUtil.setObject(HOME_PRODUCT_LIST, GsonUtil.obj2Json(homeProductVOList));
                return homeProductVOList;
            }
            return GsonUtil.json2List(str, HomeProductVO[].class);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<HomeProductVO> listHomeCategoryProductVOS() {
        RLock lock = redissonClient.getLock(HOME_CATEGORY_PRODUCT_LIST_LOCK);
        lock.lock();
        try {
            String str = (String) redisUtil.getObject(HOME_CATEGORY_PRODUCT_LIST);
            if (StringUtils.isEmpty(str)) {
                List<HomeProductVO> homeProductVOList = getHomeCategoryProductVOResources();
                /* 返回结果为空，key存null值解决缓存穿透 */
                if (CollectionUtils.isEmpty(homeProductVOList)) {
                    redisUtil.setObject(HOME_CATEGORY_PRODUCT_LIST, PRESENT, 43200L);
                }
                redisUtil.setObject(HOME_CATEGORY_PRODUCT_LIST, GsonUtil.obj2Json(homeProductVOList));
                return homeProductVOList;
            }
            return GsonUtil.json2List(str, HomeProductVO[].class);
        } finally {
            lock.unlock();
        }
    }

    private List<HomeProductVO> getHomeCategoryProductVOResources() {
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

    private List<HomeProductVO> getHomeProductVOResources() {
        List<Category> categoryList = categoryService.listCategories();
        Set<Integer> categoryIdSet = categoryList.stream().map(Category::getId).collect(Collectors.toSet());
        List<Spu> spuList = spuService.listSpuSByCategoryIdSet(categoryIdSet);
        List<HomeProductVO> homeProductVOList = new ArrayList<>();
        for (Category category : categoryList) {
            List<ProductVO> productVOList = spuList.stream()
                    .filter(spu -> category.getId().equals(spu.getCategoryId()))
                    .map(this::spu2ProductVO)
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
        return productVO;
    }

}
