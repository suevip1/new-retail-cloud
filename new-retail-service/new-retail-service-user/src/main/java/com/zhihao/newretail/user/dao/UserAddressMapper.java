package com.zhihao.newretail.user.dao;

import com.zhihao.newretail.user.pojo.UserAddress;

import java.util.List;

public interface UserAddressMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserAddress record);

    int insertSelective(UserAddress record);

    UserAddress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAddress record);

    int updateByPrimaryKey(UserAddress record);

    /* 用户id查询 */
    List<UserAddress> selectListByUserId(Integer userId);

}
