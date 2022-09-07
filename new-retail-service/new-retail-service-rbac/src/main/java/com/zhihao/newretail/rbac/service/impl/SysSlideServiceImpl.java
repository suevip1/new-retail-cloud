package com.zhihao.newretail.rbac.service.impl;

import com.zhihao.newretail.api.file.feign.FileUploadFeignService;
import com.zhihao.newretail.file.consts.FileUploadDirConst;
import com.zhihao.newretail.rbac.service.SysSlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SysSlideServiceImpl implements SysSlideService {

    @Autowired
    private FileUploadFeignService fileUploadFeignService;

    @Override
    public String uploadSlideImage(MultipartFile file) throws IOException {
        return fileUploadFeignService.getFileUrl(file, FileUploadDirConst.SLIDER_IMG);
    }

}
