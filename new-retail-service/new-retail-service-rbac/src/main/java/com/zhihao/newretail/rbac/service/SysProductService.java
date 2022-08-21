package com.zhihao.newretail.rbac.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SysProductService {

    /*
    * 新增商品
    * */
    void addSpu(Integer categoryId, String title, String subTitle,
                MultipartFile showImage, MultipartFile[] sliderImage, String detailTitle,
                String detailPram, MultipartFile[] detailImage) throws IOException;

    /*
    * 修改商品
    * */
    void updateSpu(Integer spuId, Integer categoryId, String title,
                   String subTitle, MultipartFile showImage, MultipartFile[] sliderImage,
                   String detailTitle, String detailPram, MultipartFile[] detailImage) throws IOException;

}
