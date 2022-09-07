package com.zhihao.newretail.product.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.product.pojo.vo.HomeProductVO;
import com.zhihao.newretail.product.pojo.vo.SlideVO;
import com.zhihao.newretail.product.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadPoolExecutor;

import static com.zhihao.newretail.product.consts.ProductCacheConst.*;

@RestController
public class HomeController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private ThreadPoolExecutor executor;

    @GetMapping("/home")
    public R home() {
        ConcurrentMap<String, Object> listConcurrentMap = new ConcurrentHashMap<>();
        /* 轮播图 */
        CompletableFuture<Void> homeSlideVOListFuture = CompletableFuture.runAsync(() -> {
            List<SlideVO> slideVOList = homeService.listSlideVOS();
            listConcurrentMap.put(HOME_SLIDE_LIST, slideVOList);
        }, executor);
        /* 首页nav分类商品 */
        CompletableFuture<Void> homeNavCategoryProductVOListFuture = CompletableFuture.runAsync(() -> {
            List<HomeProductVO> homeNavCategoryProductVOList = homeService.listHomeNavCategoryProductVOS();
            listConcurrentMap.put(HOME_NAV_CATEGORY_PRODUCT_LIST, homeNavCategoryProductVOList);
        }, executor);
        /* 首页菜单分类商品 */
        CompletableFuture<Void> homeCategoryProductVOListFuture = CompletableFuture.runAsync(() -> {
            List<HomeProductVO> homeCategoryProductVOList = homeService.listHomeCategoryProductVOS();
            listConcurrentMap.put(HOME_CATEGORY_PRODUCT_LIST, homeCategoryProductVOList);
        }, executor);
        /* 首页商品 */
        CompletableFuture<Void> homeProductVOListFuture = CompletableFuture.runAsync(() -> {
            List<HomeProductVO> homeProductVOList = homeService.listHomeProductVOS();
            listConcurrentMap.put(HOME_PRODUCT_LIST, homeProductVOList);
        }, executor);
        CompletableFuture.allOf(homeSlideVOListFuture, homeNavCategoryProductVOListFuture, homeCategoryProductVOListFuture, homeProductVOListFuture).join();
        return R.ok().put("data", listConcurrentMap);
    }

}
