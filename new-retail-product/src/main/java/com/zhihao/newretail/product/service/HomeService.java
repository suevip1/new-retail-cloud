package com.zhihao.newretail.product.service;

import com.zhihao.newretail.product.vo.HomeProductVO;
import com.zhihao.newretail.product.vo.SlideVO;

import java.util.List;

public interface HomeService {

    /*
    * 首页商品
    * */
    List<HomeProductVO> listHomeProductVOS();

    /*
    * 首页分类商品
    * */
    List<HomeProductVO> listHomeCategoryProductVOS();

    /*
    * 首页导航分类商品
    * */
    List<HomeProductVO> listHomeNavCategoryProductVOS();

    /*
    * 首页轮播图
    * */
    List<SlideVO> listSlideVOS();

}
