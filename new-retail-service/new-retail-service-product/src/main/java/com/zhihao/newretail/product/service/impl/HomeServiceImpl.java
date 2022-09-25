package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.core.util.BeanCopyUtil;
import com.zhihao.newretail.core.util.GsonUtil;
import com.zhihao.newretail.product.factory.HomeProductResourcesFactory;
import com.zhihao.newretail.product.pojo.vo.HomeProductVO;
import com.zhihao.newretail.product.pojo.vo.SlideVO;
import com.zhihao.newretail.product.service.HomeService;
import com.zhihao.newretail.product.service.SlideService;
import com.zhihao.newretail.redis.util.MyRedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.zhihao.newretail.product.consts.ProductCacheKeyConst.*;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private HomeProductResourcesFactory resourcesFactory;

    @Autowired
    private SlideService slideService;

    @Autowired
    private MyRedisUtil redisUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public List<HomeProductVO> listHomeProductVOS() {
        return getData(HOME_PRODUCT_LIST, HOME_PRODUCT_LIST_LOCK);
    }

    @Override
    public List<HomeProductVO> listHomeCategoryProductVOS() {
        return getData(HOME_CATEGORY_PRODUCT_LIST, HOME_CATEGORY_PRODUCT_LIST_LOCK);
    }

    @Override
    public List<HomeProductVO> listHomeNavCategoryProductVOS() {
        return getData(HOME_NAV_CATEGORY_PRODUCT_LIST, HOME_NAV_CATEGORY_PRODUCT_LIST_LOCK);
    }

    @Override
    public List<SlideVO> listSlideVOS() {
        RLock lock = redissonClient.getLock(HOME_SLIDE_LIST_LOCK);
        lock.lock();
        try {
            String str = (String) redisUtil.getObject(HOME_SLIDE_LIST);
            if (StringUtils.isEmpty(str)) {
                List<SlideVO> slideVOList = slideService.listSlideS().stream().map(slide -> BeanCopyUtil.source2Target(slide, SlideVO.class)).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(slideVOList)) {
                    redisUtil.setObject(HOME_SLIDE_LIST, PRESENT, 43200L);
                } else {
                    redisUtil.setObject(HOME_SLIDE_LIST, GsonUtil.obj2Json(slideVOList));
                }
                return slideVOList;
            }
            return GsonUtil.json2List(str, SlideVO[].class);
        } finally {
            lock.unlock();
        }
    }

    private List<HomeProductVO> getData(String cacheKey, String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        try {
            String str = (String) redisUtil.getObject(cacheKey);
            if (StringUtils.isEmpty(str)) {
                List<HomeProductVO> homeProductVOList = resourcesFactory.getResources(cacheKey).listHomeProductVOS();
                /* 返回结果为空，key存null值解决缓存穿透 */
                if (CollectionUtils.isEmpty(homeProductVOList)) {
                    redisUtil.setObject(cacheKey, PRESENT, 43200L);
                } else {
                    redisUtil.setObject(cacheKey, GsonUtil.obj2Json(homeProductVOList));
                }
                return homeProductVOList;
            }
            return GsonUtil.json2List(str, HomeProductVO[].class);
        } finally {
            lock.unlock();
        }
    }

}
