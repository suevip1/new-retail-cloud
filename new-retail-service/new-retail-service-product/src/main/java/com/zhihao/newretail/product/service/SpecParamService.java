package com.zhihao.newretail.product.service;

import com.zhihao.newretail.api.product.dto.SpecParamAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpecParamUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.SpecParamApiVO;

import java.util.List;

public interface SpecParamService {

    /*
    * 分类通用参数列表
    * */
    List<SpecParamApiVO> listSpecParamApiVOS(Integer categoryId);

    /*
    * 新增分类参数
    * */
    void insertSpecParamKey(SpecParamAddApiDTO specParamAddApiDTO);

    /*
    * 修改分类参数
    * */
    void updateSpecParamKey(Integer specParamId, SpecParamUpdateApiDTO specParamUpdateApiDTO);

    /*
    * 删除分类参数
    * */
    void deleteSpecParamKey(Integer specParamId);

}
