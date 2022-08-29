package com.zhihao.newretail.product.feign;

import com.zhihao.newretail.api.product.dto.SpecParamAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpecParamUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.SpecParamApiVO;
import com.zhihao.newretail.product.service.SpecParamService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SpecParamFeignClient {

    @Autowired
    private SpecParamService specParamService;

    @RequiresLogin
    @GetMapping("/api/specParam/{categoryId}")
    public List<SpecParamApiVO> listSpecParamApiVOS(@PathVariable Integer categoryId) {
        List<SpecParamApiVO> specParamApiVOList = specParamService.listSpecParamApiVOS(categoryId);
        UserLoginContext.sysClean();
        return specParamApiVOList;
    }

    @RequiresLogin
    @PostMapping("/api/specParam")
    public Integer addSpecParam(@RequestBody SpecParamAddApiDTO specParamAddApiDTO) {
        int insertRow = specParamService.insertSpecParamKey(specParamAddApiDTO);
        UserLoginContext.sysClean();
        return insertRow;
    }

    @RequiresLogin
    @PutMapping("/api/specParam/{specParamId}")
    public Integer updateSpecParam(@PathVariable Integer specParamId, @RequestBody SpecParamUpdateApiDTO specParamUpdateApiDTO) {
        int updateRow = specParamService.updateSpecParamKey(specParamId, specParamUpdateApiDTO);
        UserLoginContext.sysClean();
        return updateRow;
    }

    @RequiresLogin
    @DeleteMapping("/api/specParam/{specParamId}")
    public Integer deleteSpecParam(@PathVariable Integer specParamId) {
        int deleteRow = specParamService.deleteSpecParamKey(specParamId);
        UserLoginContext.sysClean();
        return deleteRow;
    }

}
