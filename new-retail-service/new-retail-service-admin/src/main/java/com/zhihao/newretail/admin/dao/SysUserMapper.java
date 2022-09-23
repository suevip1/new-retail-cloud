package com.zhihao.newretail.admin.dao;

import com.zhihao.newretail.admin.pojo.SysUser;

import java.util.List;

public interface SysUserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    int countByUsername(String username);

    List<SysUser> selectListByAll();

    SysUser selectUserRoleByUsername(String username);

    SysUser selectUserRoleByUserId(Integer id);

}
