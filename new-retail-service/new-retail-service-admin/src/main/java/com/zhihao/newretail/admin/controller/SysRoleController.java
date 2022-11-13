package com.zhihao.newretail.admin.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.admin.form.SysRoleForm;
import com.zhihao.newretail.admin.vo.SysRoleVO;
import com.zhihao.newretail.admin.service.SysRoleService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@RestController
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/role/list")
    public R roleList() {
        List<SysRoleVO> sysRoleVOList = sysRoleService.listSysRoleVOs();
        if (!CollectionUtils.isEmpty(sysRoleVOList)) {
            return R.ok().put("data", sysRoleVOList);
        }
        return R.error(HttpStatus.SC_NO_CONTENT, "暂无数据").put("data", sysRoleVOList);
    }

    @GetMapping("/role/{roleId}")
    public R roleInfo(@PathVariable Integer roleId) {
        SysRoleVO sysRoleVO = sysRoleService.getSysRoleVO(roleId);
        if (!ObjectUtils.isEmpty(sysRoleVO)) {
            return R.ok().put("data", sysRoleVO);
        }
        return R.error(HttpStatus.SC_NO_CONTENT, "暂无数据").put("data", sysRoleVO);
    }

    @PostMapping("/role")
    public R roleAdd(@Valid @RequestBody SysRoleForm form) {
        int insertRow = sysRoleService.insertRole(form);
        if (insertRow >= 1) {
            return R.ok("新增角色成功");
        }
        return R.error("新增角色失败");
    }

    @PutMapping("/role/{roleId}")
    public R roleUpdate(@PathVariable Integer roleId, @Valid @RequestBody SysRoleForm form) {
        int updateRow = sysRoleService.updateRole(roleId, form);
        if (updateRow >= 1) {
            return R.ok("更新角色成功");
        }
        return R.error("更新角色失败");
    }

    @DeleteMapping("/role/{roleId}")
    public R roleDelete(@PathVariable Integer roleId) {
        int deleteRow = sysRoleService.deleteRole(roleId);
        if (deleteRow >= 1) {
            return R.ok("删除角色成功");
        }
        return R.error("删除角色失败");
    }

}
