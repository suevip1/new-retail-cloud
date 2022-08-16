package com.zhihao.newretail.rbac.service.impl;

import com.zhihao.newretail.rbac.dao.SysRoleMapper;
import com.zhihao.newretail.rbac.pojo.SysRole;
import com.zhihao.newretail.rbac.pojo.vo.SysRoleVO;
import com.zhihao.newretail.rbac.service.SysRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private SysRoleVO sysRole2SysRoleVO(SysRole sysRole) {
        SysRoleVO sysRoleVO = new SysRoleVO();
        BeanUtils.copyProperties(sysRole, sysRoleVO);
        return sysRoleVO;
    }

}
