package com.zhihao.newretail.product.service;

import com.zhihao.newretail.api.product.dto.SpuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpuUpdateApiDTO;

public interface SpuService {

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

}
