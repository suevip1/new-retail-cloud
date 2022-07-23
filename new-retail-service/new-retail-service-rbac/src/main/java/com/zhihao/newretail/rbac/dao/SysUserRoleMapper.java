package com.zhihao.newretail.rbac.dao;

import com.zhihao.newretail.rbac.pojo.SysUserRoleKey;

public interface SysUserRoleMapper {

    int deleteByPrimaryKey(SysUserRoleKey key);

    int insert(SysUserRoleKey record);

    int insertSelective(SysUserRoleKey record);

}
