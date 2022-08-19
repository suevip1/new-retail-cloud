package com.zhihao.newretail.product.feign;

import com.zhihao.newretail.api.product.dto.SpuAddApiDTO;
import com.zhihao.newretail.api.product.feign.SpuFeignService;
import com.zhihao.newretail.product.service.SpuService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpuFeignController implements SpuFeignService {

    @Autowired
    private SpuService spuService;

    @Override
    @RequiresLogin
    public void addSpu(SpuAddApiDTO spuAddApiDTO) {
        spuService.insertSpu(spuAddApiDTO);
        UserLoginContext.sysClean();
    }

}
