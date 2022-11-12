package com.zhihao.newretail.product.feign;

import com.zhihao.newretail.api.product.dto.SpecParamAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpecParamUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.SpecParamApiVO;
import com.zhihao.newretail.product.service.SpecParamService;
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

import java.util.List;

@RestController
@RequestMapping("/feign")
public class SpecParamFeignClient {

    @Autowired
    private SpecParamService specParamService;

    @RequiresLogin
    @GetMapping("/spec-param/{categoryId}")
    public List<SpecParamApiVO> listSpecParamApiVOS(@PathVariable Integer categoryId) {
        List<SpecParamApiVO> specParamApiVOList = specParamService.listSpecParamApiVOS(categoryId);
        UserLoginContext.sysClean();
        return specParamApiVOList;
    }

    @RequiresLogin
    @PostMapping("/spec-param")
    public Integer addSpecParam(@RequestBody SpecParamAddApiDTO specParamAddApiDTO) {
        int insertRow = specParamService.insertSpecParamKey(specParamAddApiDTO);
        UserLoginContext.sysClean();
        return insertRow;
    }

    @RequiresLogin
    @PutMapping("/spec-param/{specParamId}")
    public Integer updateSpecParam(@PathVariable Integer specParamId, @RequestBody SpecParamUpdateApiDTO specParamUpdateApiDTO) {
        int updateRow = specParamService.updateSpecParamKey(specParamId, specParamUpdateApiDTO);
        UserLoginContext.sysClean();
        return updateRow;
    }

    @RequiresLogin
    @DeleteMapping("/spec-param/{specParamId}")
    public Integer deleteSpecParam(@PathVariable Integer specParamId) {
        int deleteRow = specParamService.deleteSpecParamKey(specParamId);
        UserLoginContext.sysClean();
        return deleteRow;
    }

}
