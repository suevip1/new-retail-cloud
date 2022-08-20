package com.zhihao.newretail.product.feign;

import com.zhihao.newretail.api.product.dto.SkuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SkuUpdateApiDTO;
import com.zhihao.newretail.api.product.feign.SkuFeignService;
import com.zhihao.newretail.product.service.SkuService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SkuFeignController implements SkuFeignService {

    @Autowired
    private SkuService skuService;

    @Override
    @RequiresLogin
    public void addSku(SkuAddApiDTO skuAddApiDTO) {
        skuService.insertSku(skuAddApiDTO);
        UserLoginContext.sysClean();
    }

    @Override
    @RequiresLogin
    public void updateSku(Integer skuId, SkuUpdateApiDTO skuUpdateApiDTO) {
        skuService.updateSku(skuId, skuUpdateApiDTO);
        UserLoginContext.sysClean();
    }

    @Override
    @RequiresLogin
    public void deleteSku(Integer skuId) {
        skuService.deleteSku(skuId);
        UserLoginContext.sysClean();
    }

}
