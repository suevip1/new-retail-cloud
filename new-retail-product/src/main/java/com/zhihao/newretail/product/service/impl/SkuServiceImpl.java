package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.api.product.dto.SkuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SkuUpdateApiDTO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.product.dao.SkuMapper;
import com.zhihao.newretail.product.dao.SkuStockMapper;
import com.zhihao.newretail.product.pojo.Sku;
import com.zhihao.newretail.product.pojo.SkuStock;
import com.zhihao.newretail.product.service.SkuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SkuStockMapper skuStockMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSku(SkuAddApiDTO skuAddApiDTO) {
        Sku sku = new Sku();
        BeanUtils.copyProperties(skuAddApiDTO, sku);
        int insertSkuRow = skuMapper.insertSelective(sku);
        if (insertSkuRow >= 1) {
            insertSkuStock(sku.getId(), skuAddApiDTO.getStock());
            return insertSkuRow;
        }
        throw new ServiceException("新增商品规格失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSku(Integer skuId, SkuUpdateApiDTO skuUpdateApiDTO) {
        Sku sku = new Sku();
        BeanUtils.copyProperties(skuUpdateApiDTO, sku);
        sku.setId(skuId);
        int updateSkuRow = skuMapper.updateByPrimaryKeySelective(sku);
        if (updateSkuRow >= 1) {
            updateSkuStock(skuId, skuUpdateApiDTO.getStock());
            return updateSkuRow;
        }
        throw new ServiceException("修改商品规格失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteSku(Integer skuId) {
        int deleteSkuRow = skuMapper.deleteByPrimaryKey(skuId);
        if (deleteSkuRow >= 1) {
            deleteSkuStock(skuId);
            return deleteSkuRow;
        }
        throw new ServiceException("删除商品规格失败");
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

    /*
     * 保存商品库存
     * */
    private void insertSkuStock(Integer skuId, Integer stockNum) {
        SkuStock skuStock = new SkuStock();
        skuStock.setSkuId(skuId);
        skuStock.setActualStock(stockNum);
        skuStock.setStock(stockNum);
        int insertStockRow = skuStockMapper.insertSelective(skuStock);
        if (insertStockRow <= 0) {
            throw new ServiceException("商品库存保存失败");
        }
    }

    /*
    * 修改商品库存
    * */
    private void updateSkuStock(Integer skuId, Integer stockNum) {
        SkuStock skuStock = new SkuStock();
        skuStock.setSkuId(skuId);
        skuStock.setActualStock(stockNum);
        skuStock.setStock(stockNum);
        int updateStockRow = skuStockMapper.updateByPrimaryKeySelective(skuStock);
        if (updateStockRow <= 0) {
            throw new ServiceException("商品库存修改失败");
        }
    }

    /*
    * 删除商品库存
    * */
    private void deleteSkuStock(Integer skuId) {
        int deleteStockRow = skuStockMapper.deleteBySkuId(skuId);
        if (deleteStockRow <= 0) {
            throw new ServiceException("商品库存删除失败");
        }
    }

}
