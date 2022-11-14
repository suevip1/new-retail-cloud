package com.zhihao.newretail.product.service;

import com.zhihao.newretail.api.product.dto.SkuStockLockApiDTO;
import com.zhihao.newretail.api.product.vo.SkuStockApiVO;
import com.zhihao.newretail.product.enums.SkuStockTypeEnum;
import com.zhihao.newretail.product.pojo.SkuStockLock;

import java.util.List;
import java.util.Set;

public interface StockService {

    /*
     * 批量获取商品库存信息
     * */
    List<SkuStockApiVO> listSkuStockApiVOS(Set<Integer> skuIdSet);

    /*
    * 锁定商品库存
    * */
    void stockLock(SkuStockLockApiDTO skuStockLockApiDTO);

    /*
    * 批量锁定商品库存
    * */
    int stockBatchLock(List<SkuStockLockApiDTO> skuStockLockApiDTOList);

    /*
    * 获取库存锁定信息列表
    * */
    List<SkuStockLock> listSkuStockLockS(Long orderId);

    /*
    * 解锁库存 删减库存
    * */
    void updateStockByType(Long orderId, List<SkuStockLock> skuStockLockList, SkuStockTypeEnum skuStockTypeEnum);

}
