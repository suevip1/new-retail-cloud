package com.zhihao.newretail.rbac.service.impl;

import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.MyMD5SecretUtil;
import com.zhihao.newretail.rbac.dao.SysUserMapper;
import com.zhihao.newretail.rbac.dao.SysUserRoleMapper;
import com.zhihao.newretail.rbac.pojo.SysRole;
import com.zhihao.newretail.rbac.pojo.SysUser;
import com.zhihao.newretail.rbac.pojo.SysUserRoleKey;
import com.zhihao.newretail.rbac.pojo.dto.SysUserAddDTO;
import com.zhihao.newretail.rbac.pojo.vo.SysRoleVO;
import com.zhihao.newretail.rbac.pojo.vo.SysUserVO;
import com.zhihao.newretail.rbac.service.SysUserService;
import org.apache.http.HttpStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysUserVO> listSysUserVOs() {
        List<SysUser> sysUserList = sysUserMapper.selectListByAll();
        List<SysUserVO> sysUserVOList = new ArrayList<>();
        sysUserList.stream()
                .sorted(Comparator.comparing(SysUser::getCreateTime))
                .forEach(sysUser -> {
                    SysUserVO sysUserVO = sysUser2SysUserVO(sysUser);
                    SysRoleVO sysRoleVO = sysRole2SysRoleVO(sysUser.getSysRole());
                    sysUserVO.setSysRoleVO(sysRoleVO);
                    sysUserVOList.add(sysUserVO);
                });
        return sysUserVOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSysUser(SysUserAddDTO userAddDTO) {
        String username = userAddDTO.getUsername();
        String password = userAddDTO.getPassword();
        Integer roleId = userAddDTO.getRoleId();

        countUsername(username);        // 校验用户是否存在
        String secretPassword = MyMD5SecretUtil.getSecretPassword(password, username);      // 密码MD5加盐
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword(secretPassword);
        int insertRow = sysUserMapper.insertSelective(sysUser);
        insertUserRole(sysUser.getId(), roleId);

        if (insertRow <= 0) {
            throw new ServiceException("创建用户失败");
        }
        return insertRow;
    }

    /*
    * 用户角色关联
    * */
    private void insertUserRole(Integer userId, Integer roleId) {
        SysUserRoleKey userRole = new SysUserRoleKey();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        sysUserRoleMapper.insertSelective(userRole);
    }

    /*
    * 验证用户是否存在
    * */
    private void countUsername(String username) {
        int count = sysUserMapper.countByUsername(username);
        if (count > 0) {
            throw new ServiceException(HttpStatus.SC_CREATED, "用户已存在");
        }
    }

    private SysUserVO sysUser2SysUserVO(SysUser sysUser) {
        SysUserVO sysUserVO = new SysUserVO();
        BeanUtils.copyProperties(sysUser, sysUserVO);
        return sysUserVO;
    }

    private SysRoleVO sysRole2SysRoleVO(SysRole sysRole) {
        SysRoleVO sysRoleVO = new SysRoleVO();
        BeanUtils.copyProperties(sysRole, sysRoleVO);
        return sysRoleVO;
    }

}
