package com.zhihao.newretail.rbac.service;

import com.zhihao.newretail.api.product.vo.SpecParamApiVO;

import java.util.List;

public interface SysSpecParamService {

    /*
     * 分类通用参数列表
     * */
    List<SpecParamApiVO> listSpecParamApiVOs(Integer categoryId);

}
