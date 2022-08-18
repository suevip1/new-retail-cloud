package com.zhihao.newretail.product.service;

import com.zhihao.newretail.api.product.vo.SpecParamApiVO;

import java.util.List;

public interface SpecParamService {

    /*
    * 分类通用参数列表
    * */
    List<SpecParamApiVO> listSpecParamApiVOs(Integer categoryId);

}
