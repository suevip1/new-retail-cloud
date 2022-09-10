package com.zhihao.newretail.product.service;

import com.zhihao.newretail.api.product.dto.SlideAddApiDTO;
import com.zhihao.newretail.api.product.dto.SlideUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.SlideApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.product.pojo.Slide;

import java.util.List;

public interface SlideService {

    /*
    * 首页轮播图列表
    * */
    List<Slide> listSlideS();

    /*
    * 轮播图列表(分页)
    * */
    PageUtil<SlideApiVO> listSlideApiVOS(Integer slideId, Integer pageNum, Integer pageSize);

    /*
    * 新增轮播图
    * */
    int insertSlide(SlideAddApiDTO slideAddApiDTO);

    /*
    * 修改轮播图
    * */
    int updateSlide(Integer slideId, SlideUpdateApiDTO slideUpdateApiDTO);

    /*
    * 删除轮播图
    * */
    int deleteSlide(Integer slideId);

}
