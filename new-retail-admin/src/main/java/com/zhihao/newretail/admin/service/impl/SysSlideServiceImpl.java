package com.zhihao.newretail.admin.service.impl;

import com.zhihao.newretail.api.file.feign.FileUploadFeignService;
import com.zhihao.newretail.api.product.dto.SlideAddApiDTO;
import com.zhihao.newretail.api.product.dto.SlideUpdateApiDTO;
import com.zhihao.newretail.api.product.feign.SlideFeignService;
import com.zhihao.newretail.api.product.vo.SlideApiVO;
import com.zhihao.newretail.core.util.BeanCopyUtil;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.file.consts.FileUploadDirConst;
import com.zhihao.newretail.admin.annotation.RequiresPermission;
import com.zhihao.newretail.admin.consts.AuthorizationConst;
import com.zhihao.newretail.admin.form.SlideForm;
import com.zhihao.newretail.admin.service.SysSlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SysSlideServiceImpl implements SysSlideService {

    @Autowired
    private SlideFeignService slideFeignService;

    @Autowired
    private FileUploadFeignService fileUploadFeignService;

    @Override
    @RequiresPermission(scope = AuthorizationConst.COMMON)
    public PageUtil<SlideApiVO> listSlideApiVOS(Integer slideId, Integer pageNum, Integer pageSize) {
        return slideFeignService.listSlideApiVOS(slideId, pageNum, pageSize);
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.ADMIN)
    public Integer insertSlide(SlideForm form) {
        return slideFeignService.addSlide(BeanCopyUtil.source2Target(form, SlideAddApiDTO.class));
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.ADMIN)
    public Integer updateSlide(Integer slideId, SlideForm form) {
        return slideFeignService.updateSlide(slideId, BeanCopyUtil.source2Target(form, SlideUpdateApiDTO.class));
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.ROOT)
    public Integer deleteSlide(Integer slideId) {
        return slideFeignService.deleteSlide(slideId);
    }

    @Override
    public String uploadSlideImage(MultipartFile file) throws IOException {
        return fileUploadFeignService.getFileUrl(file, FileUploadDirConst.SLIDER_IMG);
    }

}
