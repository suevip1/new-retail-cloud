package com.zhihao.newretail.admin.service.impl;

import com.zhihao.newretail.api.product.dto.SpecParamAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpecParamUpdateApiDTO;
import com.zhihao.newretail.api.product.feign.SpecParamFeignService;
import com.zhihao.newretail.api.product.vo.SpecParamApiVO;
import com.zhihao.newretail.admin.annotation.RequiresPermission;
import com.zhihao.newretail.admin.consts.AuthorizationConst;
import com.zhihao.newretail.admin.service.SysSpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysSpecParamServiceImpl implements SysSpecParamService {

    @Autowired
    private SpecParamFeignService specParamFeignService;

    @Override
    @RequiresPermission(scope = AuthorizationConst.COMMON)
    public List<SpecParamApiVO> listSpecParamApiVOS(Integer categoryId) {
        return specParamFeignService.listSpecParamApiVOS(categoryId);
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.ADMIN)
    public Integer addSpecParam(SpecParamAddApiDTO specParamAddApiDTO) {
        return specParamFeignService.addSpecParam(specParamAddApiDTO);
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.ADMIN)
    public Integer updateSpecParam(Integer specParamId, SpecParamUpdateApiDTO specParamUpdateApiDTO) {
        return specParamFeignService.updateSpecParam(specParamId, specParamUpdateApiDTO);
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.ROOT)
    public Integer deleteSpecParam(Integer specParamId) {
        return specParamFeignService.deleteSpecParam(specParamId);
    }

}
