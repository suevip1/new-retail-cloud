package com.zhihao.newretail.product.service;

import com.zhihao.newretail.api.product.dto.SpuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpuUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.SpuApiVO;
import com.zhihao.newretail.product.pojo.Spu;

import java.util.List;
import java.util.Set;

public interface SpuService {

    /*
    * 获取商品信息(Feign)
    * */
    SpuApiVO getSpuApiVO(Integer spuId);

    /*
    * 新增商品
    * */
    int insertSpu(SpuAddApiDTO spuAddApiDTO);

    /*
    * 修改商品
    * */
    int updateSpu(Integer spuId, SpuUpdateApiDTO spuUpdateApiDTO);

    /*
    * 删除商品
    * */
    int deleteSpu(Integer spuId);

    /*
    * countByCategoryId
    * */
    int countByCategoryId(Integer categoryId);

    /*
    * 获取spu
    * */
    Spu getSpu(Integer spuId);

    /*
    * categoryId获取spuList
    * */
    List<Spu> listSpuS(Integer categoryId, Integer pageNum, Integer pageSize);

    /*
    * categoryId批量获取spuList
    * */
    List<Spu> listSpuSByCategoryIdSet(Set<Integer> categoryIdSet);

    /*
    * 批量获取spuList
    * */
    List<Spu> listSpuS(Set<Integer> idSet);

}
