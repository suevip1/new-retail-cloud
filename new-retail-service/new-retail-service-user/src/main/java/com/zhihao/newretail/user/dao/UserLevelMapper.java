package com.zhihao.newretail.user.dao;

import com.zhihao.newretail.user.pojo.UserLevel;

public interface UserLevelMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserLevel record);

    int insertSelective(UserLevel record);

    UserLevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLevel record);

    int updateByPrimaryKey(UserLevel record);

}
