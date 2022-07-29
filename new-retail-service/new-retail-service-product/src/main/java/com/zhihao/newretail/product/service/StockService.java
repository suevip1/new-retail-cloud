package com.zhihao.newretail.product.service;

import com.zhihao.newretail.api.product.dto.SkuStockLockApiDTO;
import com.zhihao.newretail.api.product.vo.SkuStockApiVO;
import com.zhihao.newretail.product.pojo.SkuStock;

import java.util.List;
import java.util.Set;

public interface StockService {

    /*
    * 获取商品库存信息
    * */
    SkuStock getSkuStock(Integer skuId);

    /*
     * 批量获取商品库存信息
     * */
    List<SkuStockApiVO> listSkuStockApiVOs(Set<Integer> skuIdSet);

    /*
    * 锁定商品库存
    * */
    void stockLock(SkuStockLockApiDTO skuStockLockApiDTO);

}
