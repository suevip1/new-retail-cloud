package com.zhihao.newretail.product.feign;

import com.zhihao.newretail.api.product.dto.SkuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SkuUpdateApiDTO;
import com.zhihao.newretail.product.service.SkuService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feign")
public class SkuFeignClient {

    @Autowired
    private SkuService skuService;

    @RequiresLogin
    @PostMapping("/sku")
    public Integer addSku(@RequestBody SkuAddApiDTO skuAddApiDTO) {
        int insertRow = skuService.insertSku(skuAddApiDTO);
        UserLoginContext.sysClean();
        return insertRow;
    }

    @RequiresLogin
    @PutMapping("/sku/{skuId}")
    public Integer updateSku(@PathVariable Integer skuId, @RequestBody SkuUpdateApiDTO skuUpdateApiDTO) {
        int updateRow = skuService.updateSku(skuId, skuUpdateApiDTO);
        UserLoginContext.sysClean();
        return updateRow;
    }

    @RequiresLogin
    @DeleteMapping("/sku/{skuId}")
    public Integer deleteSku(@PathVariable Integer skuId) {
        int deleteRow = skuService.deleteSku(skuId);
        UserLoginContext.sysClean();
        return deleteRow;
    }

}
