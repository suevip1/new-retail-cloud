package com.zhihao.newretail.product.feign;

import com.zhihao.newretail.api.product.dto.SpuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpuUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.SpuApiVO;
import com.zhihao.newretail.product.service.SpuService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign")
public class SpuFeignClient {

    @Autowired
    private SpuService spuService;

    @RequiresLogin
    @GetMapping("/spu/{spuId}")
    public SpuApiVO getSpuApiVO(@PathVariable Integer spuId) {
        SpuApiVO spuApiVO = spuService.getSpuApiVO(spuId);
        UserLoginContext.sysClean();
        return spuApiVO;
    }

    @RequiresLogin
    @PostMapping("/spu")
    public Integer addSpu(@RequestBody SpuAddApiDTO spuAddApiDTO) {
        int insertRow = spuService.insertSpu(spuAddApiDTO);
        UserLoginContext.sysClean();
        return insertRow;
    }

    @RequiresLogin
    @PutMapping("/spu/{spuId}")
    public Integer updateSpu(@PathVariable Integer spuId, @RequestBody SpuUpdateApiDTO spuUpdateApiDTO) {
        int updateRow = spuService.updateSpu(spuId, spuUpdateApiDTO);
        UserLoginContext.sysClean();
        return updateRow;
    }

    @RequiresLogin
    @DeleteMapping("/spu/{spuId}")
    public Integer deleteSpu(@PathVariable Integer spuId) {
        int deleteRow = spuService.deleteSpu(spuId);
        UserLoginContext.sysClean();
        return deleteRow;
    }

}
