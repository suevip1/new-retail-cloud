package com.zhihao.newretail.admin.service;

import com.zhihao.newretail.api.admin.vo.SysUserApiVO;
import com.zhihao.newretail.admin.pojo.SysUser;
import com.zhihao.newretail.admin.form.SysUserForm;
import com.zhihao.newretail.admin.pojo.vo.SysUserVO;

import java.util.List;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public interface SysUserService {

    /*
    * 系统用户列表
    * */
    List<SysUserVO> listSysUserVOS();

    /*
    * 新增系统用户
    * */
    int insertSysUser(SysUserForm form);

    /*
    * 修改系统用户
    * */
    int updateSysUser(Integer userId, SysUserForm form);

    /*
    * 删除系统用户
    * */
    int deleteSysUser(Integer userId);

    /*
    * 获取系统用户信息
    * */
    SysUserApiVO getSysUserApiVO(String username);

    /*
    * 获取系统用户信息、权限信息
    * */
    SysUser getSysUser(Integer userId);

}
