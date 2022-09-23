package com.zhihao.newretail.admin.dao;

import com.zhihao.newretail.admin.pojo.SysRolePermissionsKey;

public interface SysRolePermissionsMapper {

    int deleteByPrimaryKey(SysRolePermissionsKey key);

    int insert(SysRolePermissionsKey record);

    int insertSelective(SysRolePermissionsKey record);

}
