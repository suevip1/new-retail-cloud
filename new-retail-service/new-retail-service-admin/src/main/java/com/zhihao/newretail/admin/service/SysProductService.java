package com.zhihao.newretail.admin.service;

import com.zhihao.newretail.api.product.vo.ProductApiVO;
import com.zhihao.newretail.api.product.vo.SpuApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.admin.form.SkuForm;
import com.zhihao.newretail.admin.form.SpuForm;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SysProductService {

    /*
    * 商品列表
    * */
    PageUtil<ProductApiVO> listProductApiVOS(Integer categoryId, Integer pageNum, Integer pageSize);

    /*
    * 查看商品信息
    * */
    SpuApiVO getSpuApiVO(Integer spuId);

    /*
    * 新增商品
    * */
    Integer addSpu(SpuForm form);

    /*
    * 修改商品
    * */
    Integer updateSpu(Integer spuId, SpuForm form);

    /*
    * 删除商品
    * */
    Integer deleteSpu(Integer spuId);

    /*
    * 添加商品规格
    * */
    Integer addSku(SkuForm form);

    /*
    * 修改商品规格
    * */
    Integer updateSku(Integer skuId, SkuForm form);

    /*
    * 删除商品规格
    * */
    Integer deleteSku(Integer skuId);

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
