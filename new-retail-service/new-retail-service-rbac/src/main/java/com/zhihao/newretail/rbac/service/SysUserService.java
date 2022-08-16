package com.zhihao.newretail.rbac.service;

import com.zhihao.newretail.rbac.pojo.dto.SysUserAddDTO;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public interface SysUserService {

    /*
    * 新增系统用户
    * */
    int insertSysUser(SysUserAddDTO userAddDTO);

}
