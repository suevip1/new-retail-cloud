package com.zhihao.newretail.rbac.dao;

import com.zhihao.newretail.rbac.pojo.SysUserRoleKey;
import org.apache.ibatis.annotations.Param;

public interface SysUserRoleMapper {

    int deleteByPrimaryKey(SysUserRoleKey key);

    int insert(SysUserRoleKey record);

    int insertSelective(SysUserRoleKey record);

    int updateRoleIdByUserId(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

}
