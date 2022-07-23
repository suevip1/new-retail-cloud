package com.zhihao.newretail.rbac.dao;

import com.zhihao.newretail.rbac.pojo.SysRolePermissionsKey;

public interface SysRolePermissionsMapper {

    int deleteByPrimaryKey(SysRolePermissionsKey key);

    int insert(SysRolePermissionsKey record);

    int insertSelective(SysRolePermissionsKey record);

}
