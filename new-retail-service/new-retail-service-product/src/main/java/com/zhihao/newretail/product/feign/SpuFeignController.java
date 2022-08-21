package com.zhihao.newretail.product.feign;

import com.zhihao.newretail.api.product.dto.SpuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpuUpdateApiDTO;
import com.zhihao.newretail.api.product.feign.SpuFeignService;
import com.zhihao.newretail.api.product.vo.SpuApiVO;
import com.zhihao.newretail.product.service.SpuService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class SpuFeignController implements SpuFeignService {

    @Autowired
    private SpuService spuService;

    @Override
    @RequiresLogin
    public SpuApiVO getSpuApiVO(Integer spuId) {
        SpuApiVO spuApiVO = spuService.getSpuApiVO(spuId);
        UserLoginContext.sysClean();
        return spuApiVO;
    }

    @Override
    @RequiresLogin
    public void addSpu(SpuAddApiDTO spuAddApiDTO) {
        spuService.insertSpu(spuAddApiDTO);
        UserLoginContext.sysClean();
    }

    @Override
    @RequiresLogin
    public void updateSpu(Integer spuId, SpuUpdateApiDTO spuUpdateApiDTO) {
        spuService.updateSpu(spuId, spuUpdateApiDTO);
        UserLoginContext.sysClean();
    }

    @Override
    @RequiresLogin
    public void deleteSpu(Integer spuId) throws ExecutionException, InterruptedException {
        spuService.deleteSpu(spuId);
        UserLoginContext.sysClean();
    }

}
