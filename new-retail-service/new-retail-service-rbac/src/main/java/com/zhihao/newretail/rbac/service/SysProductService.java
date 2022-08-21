package com.zhihao.newretail.rbac.service;

import com.zhihao.newretail.api.product.vo.SpuApiVO;
import com.zhihao.newretail.rbac.form.SkuForm;
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
    * 新增商品
    * */
    void addSpu(SpuForm form);

    /*
    * 修改商品
    * */
    void updateSpu(Integer spuId, SpuForm form);

    /*
    * 删除商品
    * */
    void deleteSpu(Integer spuId) throws ExecutionException, InterruptedException;

    /*
    * 添加商品规格
    * */
    void addSku(SkuForm form);

    /*
    * 修改商品规格
    * */
    void updateSku(Integer skuId, SkuForm form) throws ExecutionException, InterruptedException;

    /*
    * 删除商品规格
    * */
    void deleteSku(Integer skuId) throws ExecutionException, InterruptedException;

    /*
    * 上传商品图片
    * */
    String uploadSpuImage(MultipartFile file) throws IOException;

    /*
    * 上传商品规格图片
    * */
    String uploadSkuImage(MultipartFile file) throws IOException;

    /*
    * 上传商品轮播图
    * */
    List<String> uploadSpuSliderImage(MultipartFile[] files) throws IOException;

    /*
    * 上传商品详情图
    * */
    List<String> uploadSpuDetailImage(MultipartFile[] files) throws IOException;

}
