package com.zhihao.newretail.rbac.dao;

import com.zhihao.newretail.rbac.pojo.SysPermissions;

public interface SysPermissionsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysPermissions record);

    int insertSelective(SysPermissions record);

    SysPermissions selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysPermissions record);

    int updateByPrimaryKey(SysPermissions record);

}
