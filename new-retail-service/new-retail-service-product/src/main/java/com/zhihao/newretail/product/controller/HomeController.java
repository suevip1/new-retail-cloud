package com.zhihao.newretail.product.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.product.pojo.vo.HomeProductVO;
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
        ConcurrentMap<String, List<HomeProductVO>> listConcurrentMap = new ConcurrentHashMap<>();
        CompletableFuture<Void> homeCategoryProductVOListFuture = CompletableFuture.runAsync(() -> {
            List<HomeProductVO> homeCategoryProductVOList = homeService.listHomeCategoryProductVOS();
            listConcurrentMap.put(HOME_CATEGORY_PRODUCT_LIST, homeCategoryProductVOList);
        }, executor);
        CompletableFuture<Void> homeProductVOListFuture = CompletableFuture.runAsync(() -> {
            List<HomeProductVO> homeProductVOList = homeService.listHomeProductVOS();
            listConcurrentMap.put(HOME_PRODUCT_LIST, homeProductVOList);
        }, executor);
        CompletableFuture.allOf(homeCategoryProductVOListFuture, homeProductVOListFuture).join();
        return R.ok().put("data", listConcurrentMap);
    }

}
