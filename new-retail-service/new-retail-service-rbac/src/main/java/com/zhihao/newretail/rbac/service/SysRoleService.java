package com.zhihao.newretail.rbac.service;

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

}
