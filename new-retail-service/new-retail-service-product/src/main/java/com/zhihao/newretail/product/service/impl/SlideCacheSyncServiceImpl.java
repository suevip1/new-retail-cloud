package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.product.consts.ProductCacheKeyConst;
import com.zhihao.newretail.product.consts.TableNameConst;
import com.zhihao.newretail.product.service.ProductCacheSyncService;
import com.zhihao.newretail.redis.util.MyRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(TableNameConst.TB_SLIDE)
public class SlideCacheSyncServiceImpl implements ProductCacheSyncService {

    @Autowired
    private MyRedisUtil redisUtil;

    @Override
    public void productCacheRemove(Integer spuId) {
        redisUtil.deleteObject(ProductCacheKeyConst.HOME_SLIDE_LIST);
    }

}
