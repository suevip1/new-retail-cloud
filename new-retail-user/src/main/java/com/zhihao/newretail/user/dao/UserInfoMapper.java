package com.zhihao.newretail.user.dao;

import com.zhihao.newretail.user.pojo.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    UserInfo selectByUserId(Integer userId);

    List<UserInfo> selectListByUserIdSet(@Param("userIdSet") Set<Integer> userIdSet);

}
