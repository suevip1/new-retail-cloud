package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.api.product.dto.SkuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SkuUpdateApiDTO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.product.dao.SkuMapper;
import com.zhihao.newretail.product.pojo.Sku;
import com.zhihao.newretail.product.service.SkuService;
import com.zhihao.newretail.product.service.StockService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockService stockService;

    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSku(SkuAddApiDTO skuAddApiDTO) {
        Sku sku = new Sku();
        BeanUtils.copyProperties(skuAddApiDTO, sku);
        int insertSkuRow = skuMapper.insertSelective(sku);
        int insertStockRow = stockService.insertStock(sku.getId(), skuAddApiDTO.getStock());
        if (insertSkuRow <= 0 || insertStockRow <= 0) {
            throw new ServiceException("新增商品规格失败");
        }
        return insertSkuRow;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSku(Integer skuId, SkuUpdateApiDTO skuUpdateApiDTO) {
        CompletableFuture<Integer> skuFuture = CompletableFuture.supplyAsync(() -> {
            Sku sku = new Sku();
            BeanUtils.copyProperties(skuUpdateApiDTO, sku);
            sku.setId(skuId);
            int updateSkuRow = skuMapper.updateByPrimaryKeySelective(sku);
            if (updateSkuRow <= 0) {
                throw new ServiceException("修改商品规格失败");
            }
            return updateSkuRow;
        }, executor);
        CompletableFuture<Void> stockFuture = CompletableFuture.runAsync(() -> {
            int updateStockRow = stockService.updateStock(skuId, skuUpdateApiDTO.getStock());
            if (updateStockRow <= 0) {
                throw new ServiceException("修改商品库存失败");
            }
        }, executor);
        CompletableFuture.allOf(skuFuture, stockFuture).join();
        return skuFuture.join();
    }

    @Override
    public int deleteSku(Integer skuId) {
        CompletableFuture<Integer> deleteSkuFuture = CompletableFuture.supplyAsync(() -> {
            int deleteSkuRow = skuMapper.deleteByPrimaryKey(skuId);
            if (deleteSkuRow <= 0) {
                throw new ServiceException("删除商品规格失败");
            }
            return deleteSkuRow;
        }, executor);
        CompletableFuture<Void> deleteStockFuture = CompletableFuture.runAsync(() -> {
            int deleteStockRow = stockService.deleteStock(skuId);
            if (deleteStockRow <= 0) {
                throw new ServiceException("删除商品库存失败");
            }
        }, executor);
        CompletableFuture.allOf(deleteSkuFuture, deleteStockFuture).join();
        return deleteSkuFuture.join();
    }

    @Override
    public Sku getSku(Integer skuId) {
        return skuMapper.selectByPrimaryKey(skuId);
    }

    @Override
    public List<Sku> listSkuS(Integer spuId) {
        return skuMapper.selectListBySpuId(spuId);
    }

    @Override
    public List<Sku> listSkuS(Set<Integer> skuIdSet) {
        return skuMapper.selectListByIdSet(skuIdSet);
    }

}
