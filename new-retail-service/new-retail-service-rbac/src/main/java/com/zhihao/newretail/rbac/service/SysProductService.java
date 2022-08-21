package com.zhihao.newretail.rbac.service;

import com.zhihao.newretail.api.product.vo.SpuApiVO;
import com.zhihao.newretail.rbac.form.SpuForm;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface SysProductService {

    /*
    * 查看商品信息
    * */
    SpuApiVO getSpuApiVO(Integer spuId);

    /*
    * 修改商品
    * */
    void updateSpu(Integer spuId, Integer categoryId, String title,
                   String subTitle, MultipartFile showImage, MultipartFile[] sliderImage,
                   String detailTitle, String detailPram, MultipartFile[] detailImage) throws IOException;

    /*
    * 新增商品
    * */
    void addSpu(SpuForm form);

    /*
    * 删除商品
    * */
    void deleteSpu(Integer spuId) throws ExecutionException, InterruptedException;

    /*
    * 上传商品图片
    * */
    String uploadSpuImage(MultipartFile file) throws IOException;

    /*
    * 上传商品轮播图
    * */
    List<String> uploadSpuSliderImage(MultipartFile[] files) throws IOException;

    /*
    * 上传商品详情图
    * */
    List<String> uploadSpuDetailImage(MultipartFile[] files) throws IOException;

}
