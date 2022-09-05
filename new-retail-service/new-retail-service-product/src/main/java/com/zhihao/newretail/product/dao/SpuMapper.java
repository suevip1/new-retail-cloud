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

    int countByCategoryId(Integer categoryId);

    /*
    * 批量查询
    * */
    List<Spu> selectListByIdSet(@Param("idSet") Set<Integer> idSet);

    /*
    * categoryId批量查询spuList
    * */
    List<Spu> selectSpuSpuInfoSkuListByCategoryIdSet(@Param("categoryIdSet") Set<Integer> categoryIdSet);

    /*
    * spu spuInfo连表查询多条数据
    * */
    List<Spu> selectSpuSpuInfoListByCategoryId(Integer categoryId);

    /*
    * spu spuInfo sku连表查询多条数据
    * */
    List<Spu> selectSpuSpuInfoSkuListByCategoryId(@Param("categoryId") Integer categoryId,
                                                  @Param("pageNum") Integer pageNum,
                                                  @Param("pageSize") Integer pageSize);

    /*
    * spu spuInfo连表查询单条数据
    * */
    Spu selectSpuSpuInfoByPrimaryKey(Integer id);

}
