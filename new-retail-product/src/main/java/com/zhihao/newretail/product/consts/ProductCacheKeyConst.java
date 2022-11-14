package com.zhihao.newretail.product.consts;

public class ProductCacheKeyConst {

    public static final String PRESENT = "null";

    public static final String HOME_SLIDE_LIST = "home_slide_list";     // 首页轮播图缓存key

    public static final String HOME_PRODUCT_LIST = "home_product_list";     // 首页商品缓存key

    public static final String HOME_CATEGORY_PRODUCT_LIST = "home_category_product_list";       // 首页分类商品缓存key

    public static final String HOME_NAV_CATEGORY_PRODUCT_LIST = "home_nav_category_product_list";   // 首页导航条商品缓存key

    public static final String PRODUCT_DETAIL = "product_detail_%d";        // 商品详情缓存key

    public static final String HOME_SLIDE_LIST_LOCK = "home-slide-list-lock";       // 首页轮播图分布式锁key

    public static final String HOME_PRODUCT_LIST_LOCK = "home-product-list-lock";       // 首页商品分布式锁key

    public static final String HOME_CATEGORY_PRODUCT_LIST_LOCK = "home-category-product-list-lock";     // 首页分类商品分布式锁key

    public static final String HOME_NAV_CATEGORY_PRODUCT_LIST_LOCK = "home-nav-category-product-list-lock";     // 首页导航分类商品分布式锁key

    public static final String PRODUCT_DETAIL_LOCK = "product-detail-%d-lock";          // 商品详情分布式锁key

}
