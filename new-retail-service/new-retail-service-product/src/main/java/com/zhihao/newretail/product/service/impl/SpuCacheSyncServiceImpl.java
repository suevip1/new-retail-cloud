package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.product.consts.TableNameConst;
import com.zhihao.newretail.product.service.ProductCacheSyncService;
import com.zhihao.newretail.redis.util.MyRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.zhihao.newretail.product.consts.ProductCacheKeyConst.*;

@Service(TableNameConst.TB_SPU)
public class SpuCacheSyncServiceImpl implements ProductCacheSyncService {

    @Autowired
    private MyRedisUtil redisUtil;

    @Override
    public void productCacheRemove(Integer spuId) {
        String redisKey = String.format(PRODUCT_DETAIL, spuId);
        redisUtil.deleteObject(redisKey);
        redisUtil.deleteObject(HOME_PRODUCT_LIST);
        redisUtil.deleteObject(HOME_CATEGORY_PRODUCT_LIST);
        redisUtil.deleteObject(HOME_NAV_CATEGORY_PRODUCT_LIST);
    }

}
