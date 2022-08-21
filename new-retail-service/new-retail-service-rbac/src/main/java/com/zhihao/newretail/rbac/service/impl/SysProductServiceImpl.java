package com.zhihao.newretail.rbac.service.impl;

import com.zhihao.newretail.api.file.feign.FileUploadFeignService;
import com.zhihao.newretail.api.product.dto.SpuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpuUpdateApiDTO;
import com.zhihao.newretail.api.product.feign.SpuFeignService;
import com.zhihao.newretail.core.util.GsonUtil;
import com.zhihao.newretail.file.consts.FileUploadDirConst;
import com.zhihao.newretail.rbac.annotation.RequiresPermission;
import com.zhihao.newretail.rbac.consts.AuthorizationConst;
import com.zhihao.newretail.rbac.service.SysProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysProductServiceImpl implements SysProductService {

    @Autowired
    private SpuFeignService spuFeignService;

    @Autowired
    private FileUploadFeignService fileUploadFeignService;

    @Override
    @RequiresPermission(scope = AuthorizationConst.ADMIN)
    public void addSpu(Integer categoryId, String title, String subTitle,
                       MultipartFile showImage, MultipartFile[] sliderImage, String detailTitle,
                       String detailPram, MultipartFile[] detailImage) throws IOException {
        SpuAddApiDTO spuAddApiDTO = buildSpuAddApiDTO(
                categoryId, title, subTitle,
                showImage, sliderImage, detailTitle,
                detailPram, detailImage);
        spuFeignService.addSpu(spuAddApiDTO);
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.ADMIN)
    public void updateSpu(Integer spuId, Integer categoryId, String title,
                          String subTitle, MultipartFile showImage, MultipartFile[] sliderImage,
                          String detailTitle, String detailPram, MultipartFile[] detailImage) throws IOException {
        SpuAddApiDTO spuAddApiDTO = buildSpuAddApiDTO(
                categoryId, title, subTitle,
                showImage, sliderImage, detailTitle,
                detailPram, detailImage);
        SpuUpdateApiDTO spuUpdateApiDTO = new SpuUpdateApiDTO();
        BeanUtils.copyProperties(spuAddApiDTO, spuUpdateApiDTO);
        spuFeignService.updateSpu(spuId, spuUpdateApiDTO);
    }

    /*
    * 必传字段
    * */
    private SpuAddApiDTO buildSpuAddApiDTO(Integer categoryId, String title, String subTitle,
                                           MultipartFile showImage, MultipartFile[] sliderImage, String detailTitle,
                                           String detailPram, MultipartFile[] detailImage) throws IOException {
        SpuAddApiDTO spuAddApiDTO = new SpuAddApiDTO();
        spuAddApiDTO.setCategoryId(categoryId);     // 所属分类
        spuAddApiDTO.setTitle(title);               // 商品标题
        spuAddApiDTO.setSubTitle(subTitle);         // 商品副标题
        spuAddApiDTO.setShowImage(getShowImageUrl(showImage));      // 商品图片
        spuAddApiDTO.setDetailTitle(detailTitle);   // 商品详情标题
        /* 非必传字段 */
        if (!ObjectUtils.isEmpty(sliderImage)) {
            List<String> sliderImageUrlS = getSliderImageUrlS(sliderImage);
            spuAddApiDTO.setSliderImage(GsonUtil.obj2Json(sliderImageUrlS));    // 商品轮播图
        }
        if (!StringUtils.isEmpty(detailPram)) {
            spuAddApiDTO.setDetailPram(GsonUtil.obj2Json(detailPram));          // 商品详情参数
        }
        if (!ObjectUtils.isEmpty(detailImage)) {
            List<String> detailImageUrlS = getDetailImageUrlS(detailImage);
            spuAddApiDTO.setDetailImage(GsonUtil.obj2Json(detailImageUrlS));    // 商品详情图
        }
        return spuAddApiDTO;
    }

    /*
    * 上传商品图片
    * */
    private String getShowImageUrl(MultipartFile file) throws IOException {
        return fileUploadFeignService.getFileUrl(file, FileUploadDirConst.SHOW_IMG);
    }

    /*
    * 上传商品轮播图
    * */
    private List<String> getSliderImageUrlS(MultipartFile[] files) throws IOException {
        List<String> sliderImageUrlS = new ArrayList<>();
        for (MultipartFile file : files) {
            String sliderImageUrl = fileUploadFeignService.getFileUrl(file, FileUploadDirConst.SLIDER_IMG);
            sliderImageUrlS.add(sliderImageUrl);
        }
        return sliderImageUrlS;
    }

    /*
    * 商品商品详情图(非必传)
    * */
    private List<String> getDetailImageUrlS(MultipartFile[] files) throws IOException {
        List<String> detailImageUrlS = new ArrayList<>();
        for (MultipartFile file : files) {
            String sliderImageUrl = fileUploadFeignService.getFileUrl(file, FileUploadDirConst.DETAIL_IMG);
            detailImageUrlS.add(sliderImageUrl);
        }
        return detailImageUrlS;
    }

}
