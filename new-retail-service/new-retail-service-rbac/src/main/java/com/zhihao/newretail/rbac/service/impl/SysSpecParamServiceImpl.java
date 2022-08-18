package com.zhihao.newretail.rbac.service.impl;

import com.zhihao.newretail.api.product.dto.SpecParamAddApiDTO;
import com.zhihao.newretail.api.product.feign.SpecParamFeignService;
import com.zhihao.newretail.api.product.vo.SpecParamApiVO;
import com.zhihao.newretail.rbac.annotation.RequiresPermission;
import com.zhihao.newretail.rbac.consts.AuthorizationConst;
import com.zhihao.newretail.rbac.service.SysSpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysSpecParamServiceImpl implements SysSpecParamService {

    @Autowired
    private SpecParamFeignService specParamFeignService;

    @Override
    @RequiresPermission(scope = AuthorizationConst.COMMON)
    public List<SpecParamApiVO> listSpecParamApiVOs(Integer categoryId) {
        return specParamFeignService.listSpecParamApiVOs(categoryId);
    }

    @Override
    @RequiresPermission(scope = AuthorizationConst.ADMIN)
    public void addSpecParam(SpecParamAddApiDTO specParamAddApiDTO) {
        specParamFeignService.addSpecParam(specParamAddApiDTO);
    }

}
