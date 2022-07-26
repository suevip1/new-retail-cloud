package com.zhihao.newretail.product.dao;

import com.zhihao.newretail.product.pojo.Spu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface SpuMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Spu record);

    int insertSelective(Spu record);

    Spu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Spu record);

    int updateByPrimaryKey(Spu record);

    /*
    * 批量查询
    * */
    List<Spu> selectListByIdSet(@Param("idSet") Set<Integer> idSet);

}
