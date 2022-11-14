package com.zhihao.newretail.user.dao;

import com.zhihao.newretail.user.pojo.User;

public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /* 条件获取用户是否存在 */
    int countByScope(User user);

    /* 条件查询用户 */
    User selectByScope(User user);

}
