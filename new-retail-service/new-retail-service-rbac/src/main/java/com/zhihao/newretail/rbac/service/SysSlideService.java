package com.zhihao.newretail.rbac.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SysSlideService {

    String uploadSlideImage(MultipartFile file) throws IOException;

}
