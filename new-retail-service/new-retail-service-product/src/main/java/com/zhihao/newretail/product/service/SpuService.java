package com.zhihao.newretail.product.service;

import com.zhihao.newretail.api.product.dto.SpuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpuUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.SpuApiVO;
import com.zhihao.newretail.product.pojo.Spu;

import java.util.List;

public interface SpuService {

    /*
    * 商品信息
    * */
    SpuApiVO getSpuApiVO(Integer spuId);

    /*
    * 新增商品
    * */
    void insertSpu(SpuAddApiDTO spuAddApiDTO);

    /*
    * 修改商品
    * */
    void updateSpu(Integer spuId, SpuUpdateApiDTO spuUpdateApiDTO);

    /*
    * 删除商品
    * */
    void deleteSpu(Integer spuId);

    /*
    * categoryId获取spuList
    * */
    List<Spu> listSpuS(Integer categoryId);

}
