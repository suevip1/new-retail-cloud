package com.zhihao.newretail.product.feign;

import com.zhihao.newretail.api.product.dto.SpecParamAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpecParamUpdateApiDTO;
import com.zhihao.newretail.api.product.feign.SpecParamFeignService;
import com.zhihao.newretail.api.product.vo.SpecParamApiVO;
import com.zhihao.newretail.product.service.SpecParamService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SpecParamFeignController implements SpecParamFeignService {

    @Autowired
    private SpecParamService specParamService;

    @Override
    @RequiresLogin
    public List<SpecParamApiVO> listSpecParamApiVOs(Integer categoryId) {
        List<SpecParamApiVO> specParamApiVOList = specParamService.listSpecParamApiVOS(categoryId);
        UserLoginContext.sysClean();
        return specParamApiVOList;
    }

    @Override
    @RequiresLogin
    public void addSpecParam(SpecParamAddApiDTO specParamAddApiDTO) {
        specParamService.insertSpecParamKey(specParamAddApiDTO);
        UserLoginContext.sysClean();
    }

    @Override
    @RequiresLogin
    public void updateSpecParam(Integer specParamId, SpecParamUpdateApiDTO specParamUpdateApiDTO) {
        specParamService.updateSpecParamKey(specParamId, specParamUpdateApiDTO);
        UserLoginContext.sysClean();
    }

    @Override
    @RequiresLogin
    public void deleteSpecParam(Integer specParamId) {
        specParamService.deleteSpecParamKey(specParamId);
        UserLoginContext.sysClean();
    }

}
