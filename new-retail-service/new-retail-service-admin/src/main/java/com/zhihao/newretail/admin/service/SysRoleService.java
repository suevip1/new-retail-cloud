package com.zhihao.newretail.admin.service;

import com.zhihao.newretail.admin.form.SysRoleForm;
import com.zhihao.newretail.admin.vo.SysRoleVO;

import java.util.List;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public interface SysRoleService {

    /*
    * 用户角色列表
    * */
    List<SysRoleVO> listSysRoleVOs();

    /*
    * 查看角色详情信息
    * */
    SysRoleVO getSysRoleVO(Integer roleId);

    /*
    * 新增角色
    * */
    int insertRole(SysRoleForm form);

    /*
    * 更新角色
    * */
    int updateRole(Integer roleId, SysRoleForm form);

    /*
    * 删除角色
    * */
    int deleteRole(Integer roleId);

}
