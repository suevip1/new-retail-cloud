package com.zhihao.newretail.rbac.service;

import com.zhihao.newretail.rbac.pojo.dto.SysUserAddDTO;
import com.zhihao.newretail.rbac.pojo.dto.SysUserUpdateDTO;
import com.zhihao.newretail.rbac.pojo.vo.SysUserVO;

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
    List<SysUserVO> listSysUserVOs();

    /*
    * 新增系统用户
    * */
    int insertSysUser(SysUserAddDTO userAddDTO);

    /*
    * 修改系统用户
    * */
    int updateSysUser(Integer userId, SysUserUpdateDTO userUpdateDTO);

}
