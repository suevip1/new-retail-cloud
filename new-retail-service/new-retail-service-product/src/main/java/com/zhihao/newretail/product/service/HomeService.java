package com.zhihao.newretail.product.service;

import com.zhihao.newretail.product.pojo.vo.HomeProductVO;

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

}
