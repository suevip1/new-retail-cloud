package com.zhihao.newretail.rbac.service.impl;

import com.zhihao.newretail.api.file.feign.FileUploadFeignService;
import com.zhihao.newretail.api.product.dto.SlideAddApiDTO;
import com.zhihao.newretail.api.product.feign.SlideFeignService;
import com.zhihao.newretail.core.util.BeanCopyUtil;
import com.zhihao.newretail.file.consts.FileUploadDirConst;
import com.zhihao.newretail.rbac.annotation.RequiresPermission;
import com.zhihao.newretail.rbac.consts.AuthorizationConst;
import com.zhihao.newretail.rbac.form.SlideForm;
import com.zhihao.newretail.rbac.service.SysSlideService;
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
    @RequiresPermission(scope = AuthorizationConst.ADMIN)
    public Integer insertSlide(SlideForm form) {
        return slideFeignService.addSlide(BeanCopyUtil.source2Target(form, SlideAddApiDTO.class));
    }

    @Override
    public String uploadSlideImage(MultipartFile file) throws IOException {
        return fileUploadFeignService.getFileUrl(file, FileUploadDirConst.SLIDER_IMG);
    }

}
