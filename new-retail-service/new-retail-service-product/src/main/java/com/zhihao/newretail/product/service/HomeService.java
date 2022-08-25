package com.zhihao.newretail.product.service;

import com.zhihao.newretail.product.pojo.vo.HomeProductVO;

import java.util.List;

public interface HomeService {

    /*
    * 首页商品
    * */
    List<HomeProductVO> listHomeProductVOS();

}
