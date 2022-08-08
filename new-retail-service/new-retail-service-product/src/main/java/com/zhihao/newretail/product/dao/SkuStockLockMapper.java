package com.zhihao.newretail.product.dao;

import com.zhihao.newretail.product.pojo.SkuStockLock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SkuStockLockMapper {

    int deleteByOrderId(Long orderId);

    int insert(SkuStockLock record);

    int insertSelective(SkuStockLock record);

    SkuStockLock selectByOrderId(Long orderId);

    int updateByPrimaryKeySelective(SkuStockLock record);

    int updateByPrimaryKey(SkuStockLock record);

    /*
    * 批量插入
    * */
    int insertBatch(@Param("skuStockLockList") List<SkuStockLock> skuStockLockList);

    /*
    * 订单号查询
    * */
    List<SkuStockLock> selectListByOrderId(Long orderId);

    /*
    * 批量更新
    * */
    int updateBatch(@Param("skuStockLockList") List<SkuStockLock> skuStockLockList);

}
