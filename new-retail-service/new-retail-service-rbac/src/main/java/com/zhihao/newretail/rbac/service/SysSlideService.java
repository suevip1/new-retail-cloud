package com.zhihao.newretail.rbac.service;

import com.zhihao.newretail.api.product.vo.SlideApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.rbac.form.SlideForm;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SysSlideService {

    /*
    * 轮播图列表
    * */
    PageUtil<SlideApiVO> listSlideApiVOS(Integer slideId, Integer pageNum, Integer pageSize);

    /*
    * 新增首页轮播图
    * */
    Integer insertSlide(SlideForm form);

    /*
    * 修改首页轮播图
    * */
    Integer updateSlide(Integer slideId, SlideForm form);

    /*
    * 删除首页轮播图
    * */
    Integer deleteSlide(Integer slideId);

    String uploadSlideImage(MultipartFile file) throws IOException;

}
