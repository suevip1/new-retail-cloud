package com.zhihao.newretail.product.service;

import com.zhihao.newretail.api.product.dto.SlideAddApiDTO;
import com.zhihao.newretail.product.pojo.vo.SlideVO;

import java.util.List;

public interface SlideService {

    /*
    * 首页轮播图
    * */
    List<SlideVO> listSlideVOS();

    /*
    * 新增轮播图
    * */
    int insertSlide(SlideAddApiDTO slideAddApiDTO);

}
