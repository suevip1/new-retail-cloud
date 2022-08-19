package com.zhihao.newretail.product.service;

import com.zhihao.newretail.api.product.dto.SpuAddApiDTO;

public interface SpuService {

    /*
    * 新增商品
    * */
    void insertSpu(SpuAddApiDTO spuAddApiDTO);

}
