package com.zhihao.newretail.rbac.service;

import com.zhihao.newretail.rbac.pojo.dto.SysRoleAddDTO;
import com.zhihao.newretail.rbac.pojo.vo.SysRoleVO;

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
    int insertRole(SysRoleAddDTO roleAddDTO);

}
