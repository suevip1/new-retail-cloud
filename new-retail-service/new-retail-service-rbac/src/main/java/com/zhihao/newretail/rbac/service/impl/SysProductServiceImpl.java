package com.zhihao.newretail.rbac.service.impl;

import com.zhihao.newretail.api.file.feign.FileUploadFeignService;
import com.zhihao.newretail.api.product.dto.SpuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpuUpdateApiDTO;
import com.zhihao.newretail.api.product.feign.SpuFeignService;
import com.zhihao.newretail.api.product.vo.SpuApiVO;
import com.zhihao.newretail.core.util.GsonUtil;
import com.zhihao.newretail.file.consts.FileUploadDirConst;
import com.zhihao.newretail.rbac.annotation.RequiresPermission;
import com.zhihao.newretail.rbac.consts.AuthorizationConst;
import com.zhihao.newretail.rbac.form.SpuForm;
import com.zhihao.newretail.rbac.service.SysProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class SysProductServiceImpl implements SysProductService {

    @Autowired
    private SpuFeignService spuFeignService;

    @Autowired
    private FileUploadFeignService fileUploadFeignService;

    @Override
    @RequiresPermission(scope = AuthorizationConst.COMMON)
    public SpuApiVO getSpuApiVO(Integer spuId) {
        return spuFeignService.getSpuApiVO(spuId);
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.ADMIN)
    public void addSpu(SpuForm form) {
        SpuAddApiDTO spuAddApiDTO = spuForm2SpuAddApiDTO(form);
        spuFeignService.addSpu(spuAddApiDTO);
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.ADMIN)
    public void updateSpu(Integer spuId, SpuForm form) {
        SpuAddApiDTO spuAddApiDTO = spuForm2SpuAddApiDTO(form);
        SpuUpdateApiDTO spuUpdateApiDTO = new SpuUpdateApiDTO();
        BeanUtils.copyProperties(spuAddApiDTO, spuUpdateApiDTO);
        spuFeignService.updateSpu(spuId, spuUpdateApiDTO);
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.ROOT)
    public void deleteSpu(Integer spuId) throws ExecutionException, InterruptedException {
        spuFeignService.deleteSpu(spuId);
    }

    @Override
    public String uploadSpuImage(MultipartFile file) throws IOException {
        return fileUploadFeignService.getFileUrl(file, FileUploadDirConst.SHOW_IMG);
    }

    @Override
    public String uploadSkuImage(MultipartFile file) throws IOException {
        return fileUploadFeignService.getFileUrl(file, FileUploadDirConst.SKU_IMG);
    }

    @Override
    public List<String> uploadSpuSliderImage(MultipartFile[] files) throws IOException {
        List<String> sliderImageUrlList = new ArrayList<>();
        for (MultipartFile file : files) {
            String sliderImageUrl = fileUploadFeignService.getFileUrl(file, FileUploadDirConst.SLIDER_IMG);
            sliderImageUrlList.add(sliderImageUrl);
        }
        return sliderImageUrlList;
    }

    @Override
    public List<String> uploadSpuDetailImage(MultipartFile[] files) throws IOException {
        List<String> detailImageUrlList = new ArrayList<>();
        for (MultipartFile file : files) {
            String sliderImageUrl = fileUploadFeignService.getFileUrl(file, FileUploadDirConst.DETAIL_IMG);
            detailImageUrlList.add(sliderImageUrl);
        }
        return detailImageUrlList;
    }

    private SpuAddApiDTO spuForm2SpuAddApiDTO(SpuForm form) {
        SpuAddApiDTO spuAddApiDTO = new SpuAddApiDTO();
        BeanUtils.copyProperties(form, spuAddApiDTO);
        if (!CollectionUtils.isEmpty(form.getSliderImageUrlList())) {
            spuAddApiDTO.setSliderImage(GsonUtil.obj2Json(form.getSliderImageUrlList()));
        }
        if (!CollectionUtils.isEmpty(form.getDetailImageUrlList())) {
            spuAddApiDTO.setDetailImage(GsonUtil.obj2Json(form.getDetailImageUrlList()));
        }
        return spuAddApiDTO;
    }

}
