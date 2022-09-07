package com.zhihao.newretail.rbac.service;

import com.zhihao.newretail.rbac.form.SlideForm;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SysSlideService {

    /*
    * 新增首页轮播图
    * */
    Integer insertSlide(SlideForm form);

    /*
    * 修改首页轮播图
    * */
    Integer updateSlide(Integer slideId, SlideForm form);

    String uploadSlideImage(MultipartFile file) throws IOException;

}
