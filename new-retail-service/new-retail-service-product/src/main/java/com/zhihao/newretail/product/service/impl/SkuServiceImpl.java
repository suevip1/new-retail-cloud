package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.product.dao.SkuMapper;
import com.zhihao.newretail.product.pojo.Sku;
import com.zhihao.newretail.product.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuMapper skuMapper;

    @Override
    public Sku getSku(Integer spuId) {
        return skuMapper.selectBySpuId(spuId);
    }

    @Override
    public List<Sku> listSkuS(Integer spuId) {
        return skuMapper.selectListBySpuId(spuId);
    }

}
