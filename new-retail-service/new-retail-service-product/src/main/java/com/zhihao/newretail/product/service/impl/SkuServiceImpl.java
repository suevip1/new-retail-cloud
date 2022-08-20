package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.api.product.dto.SkuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SkuUpdateApiDTO;
import com.zhihao.newretail.product.dao.SkuMapper;
import com.zhihao.newretail.product.pojo.Sku;
import com.zhihao.newretail.product.service.SkuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuMapper skuMapper;

    @Override
    public void insertSku(SkuAddApiDTO skuAddApiDTO) {
        Sku sku = new Sku();
        BeanUtils.copyProperties(skuAddApiDTO, sku);
        skuMapper.insertSelective(sku);
    }

    @Override
    public void updateSku(Integer skuId, SkuUpdateApiDTO skuUpdateApiDTO) {
        Sku sku = new Sku();
        BeanUtils.copyProperties(skuUpdateApiDTO, sku);
        sku.setId(skuId);
        skuMapper.updateByPrimaryKeySelective(sku);
    }

    @Override
    public void deleteSku(Integer skuId) {
        skuMapper.deleteByPrimaryKey(skuId);
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
