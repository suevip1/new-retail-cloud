package com.zhihao.newretail.rbac.service;

import com.zhihao.newretail.api.product.dto.SpecParamAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpecParamUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.SpecParamApiVO;

import java.util.List;

public interface SysSpecParamService {

    /*
     * 分类通用参数列表
     * */
    List<SpecParamApiVO> listSpecParamApiVOS(Integer categoryId);

    /*
    * 新增分类参数
    * */
    Integer addSpecParam(SpecParamAddApiDTO specParamAddApiDTO);

    /*
    * 修改分类参数
    * */
    Integer updateSpecParam(Integer specParamId, SpecParamUpdateApiDTO specParamUpdateApiDTO);

    /*
    * 删除分类参数
    * */
    Integer deleteSpecParam(Integer specParamId);

}
