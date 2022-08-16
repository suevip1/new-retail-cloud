package com.zhihao.newretail.rbac.service.impl;

import com.zhihao.newretail.core.enums.DeleteEnum;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.rbac.dao.SysRoleMapper;
import com.zhihao.newretail.rbac.pojo.SysRole;
import com.zhihao.newretail.rbac.pojo.dto.SysRoleAddDTO;
import com.zhihao.newretail.rbac.pojo.dto.SysRoleUpdateDTO;
import com.zhihao.newretail.rbac.pojo.vo.SysRoleVO;
import com.zhihao.newretail.rbac.service.SysRoleService;
import org.apache.http.HttpStatus;
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
        if (ObjectUtils.isEmpty(sysRole)) {
            throw new ServiceException(HttpStatus.SC_NO_CONTENT, "角色不存在");
        }
        return sysRole2SysRoleVO(sysRole);
    }

    @Override
    public int insertRole(SysRoleAddDTO roleAddDTO) {
        SysRole sysRole = new SysRole();
        sysRole.setName(roleAddDTO.getName());
        sysRole.setKey(roleAddDTO.getKey());
        sysRole.setScope(roleAddDTO.getScope());
        int insertRow = sysRoleMapper.insertSelective(sysRole);

        if (insertRow <= 0) {
            throw new ServiceException("新增角色失败");
        }
        return insertRow;
    }

    @Override
    public int updateRole(Integer roleId, SysRoleUpdateDTO roleUpdateDTO) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
        if (ObjectUtils.isEmpty(sysRole) || DeleteEnum.DELETE.getCode().equals(sysRole.getIsDelete())) {
            throw new ServiceException("角色不存在");
        }
        sysRole.setName(roleUpdateDTO.getName());
        sysRole.setKey(roleUpdateDTO.getKey());
        sysRole.setScope(roleUpdateDTO.getScope());
        int updateRow = sysRoleMapper.updateByPrimaryKeySelective(sysRole);

        if (updateRow <= 0) {
            throw new ServiceException("更新角色失败");
        }
        return updateRow;
    }

    @Override
    public int deleteRole(Integer roleId) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
        if (ObjectUtils.isEmpty(sysRole) || DeleteEnum.DELETE.getCode().equals(sysRole.getIsDelete())) {
            throw new ServiceException("角色不存在");
        }
        sysRole.setIsDelete(DeleteEnum.DELETE.getCode());   // 使用逻辑删除
        int updateRow = sysRoleMapper.updateByPrimaryKeySelective(sysRole);

        if (updateRow <= 0) {
            throw new ServiceException("删除角色失败");
        }
        return updateRow;
    }

    private SysRoleVO sysRole2SysRoleVO(SysRole sysRole) {
        SysRoleVO sysRoleVO = new SysRoleVO();
        BeanUtils.copyProperties(sysRole, sysRoleVO);
        return sysRoleVO;
    }

}
