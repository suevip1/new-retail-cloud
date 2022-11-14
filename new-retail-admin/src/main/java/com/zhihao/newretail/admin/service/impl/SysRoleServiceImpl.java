package com.zhihao.newretail.admin.service.impl;

import com.zhihao.newretail.core.enums.DeleteEnum;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.admin.dao.SysRoleMapper;
import com.zhihao.newretail.admin.pojo.SysRole;
import com.zhihao.newretail.admin.form.SysRoleForm;
import com.zhihao.newretail.admin.vo.SysRoleVO;
import com.zhihao.newretail.admin.service.SysRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRoleVO> listSysRoleVOs() {
        List<SysRole> sysRoleList = sysRoleMapper.selectListByAll();
        return sysRoleList.stream()
                .sorted(Comparator.comparing(SysRole::getSort))
                .map(this::sysRole2SysRoleVO).collect(Collectors.toList());
    }

    @Override
    public SysRoleVO getSysRoleVO(Integer roleId) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
        if (!ObjectUtils.isEmpty(sysRole)) {
            return sysRole2SysRoleVO(sysRole);
        }
        throw new ServiceException("角色不存在");
    }

    @Override
    public int insertRole(SysRoleForm form) {
        SysRole sysRole = new SysRole();
        sysRole.setName(form.getName());
        sysRole.setKey(form.getKey());
        sysRole.setScope(form.getScope());
        int insertRow = sysRoleMapper.insertSelective(sysRole);
        if (insertRow >= 1) {
            return insertRow;
        }
        throw new ServiceException("新增角色失败");
    }

    @Override
    public int updateRole(Integer roleId, SysRoleForm form) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
        if (!ObjectUtils.isEmpty(sysRole) && DeleteEnum.NOT_DELETE.getCode().equals(sysRole.getIsDelete())) {
            sysRole.setName(form.getName());
            sysRole.setKey(form.getKey());
            sysRole.setScope(form.getScope());
            return sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        }
        throw new ServiceException("角色不存在");
    }

    @Override
    public int deleteRole(Integer roleId) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
        if (!ObjectUtils.isEmpty(sysRole) && DeleteEnum.NOT_DELETE.getCode().equals(sysRole.getIsDelete())) {
            sysRole.setIsDelete(DeleteEnum.DELETE.getCode());   // 使用逻辑删除
            return sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        }
        throw new ServiceException("角色不存在");
    }

    private SysRoleVO sysRole2SysRoleVO(SysRole sysRole) {
        SysRoleVO sysRoleVO = new SysRoleVO();
        BeanUtils.copyProperties(sysRole, sysRoleVO);
        return sysRoleVO;
    }

}
