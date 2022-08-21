package com.zhihao.newretail.product.service;

import com.zhihao.newretail.api.product.dto.SpuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpuUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.SpuApiVO;
import com.zhihao.newretail.product.pojo.Spu;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

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
    void deleteSpu(Integer spuId) throws ExecutionException, InterruptedException;

    /*
    * 获取spu
    * */
    Spu getSpu(Integer spuId);

    /*
    * categoryId获取spuList
    * */
    List<Spu> listSpuS(Integer categoryId);

    /*
    * 批量获取spuList
    * */
    List<Spu> listSpuS(Set<Integer> idSet);

}
