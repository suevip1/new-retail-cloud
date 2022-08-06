package com.zhihao.newretail.product.dao;

import com.zhihao.newretail.product.pojo.SkuStockLock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SkuStockLockMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SkuStockLock record);

    int insertSelective(SkuStockLock record);

    SkuStockLock selectByPrimaryKey(Integer id);

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
