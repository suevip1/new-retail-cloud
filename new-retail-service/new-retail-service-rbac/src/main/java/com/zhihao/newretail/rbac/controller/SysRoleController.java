package com.zhihao.newretail.rbac.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.rbac.pojo.dto.SysRoleAddDTO;
import com.zhihao.newretail.rbac.pojo.dto.SysRoleUpdateDTO;
import com.zhihao.newretail.rbac.pojo.vo.SysRoleVO;
import com.zhihao.newretail.rbac.service.SysRoleService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

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
        if (CollectionUtils.isEmpty(sysRoleVOList)) {
            return R.error(HttpStatus.SC_NO_CONTENT, "暂无数据").put("data", sysRoleVOList);
        }
        return R.ok().put("data", sysRoleVOList);
    }

    @GetMapping("/role/{roleId}")
    public R roleInfo(@PathVariable Integer roleId) {
        SysRoleVO sysRoleVO = sysRoleService.getSysRoleVO(roleId);
        if (ObjectUtils.isEmpty(sysRoleVO)) {
            return R.error(HttpStatus.SC_NO_CONTENT, "暂无数据").put("data", sysRoleVO);
        }
        return R.ok().put("data", sysRoleVO);
    }

    @PostMapping("/role")
    public R roleAdd(@Valid @RequestBody SysRoleAddDTO roleAddDTO) {
        int insertRow = sysRoleService.insertRole(roleAddDTO);
        if (insertRow <= 0) {
            return R.error("新增角色失败");
        }
        return R.ok("新增角色成功");
    }

    @PutMapping("/role/{roleId}")
    public R roleUpdate(@PathVariable Integer roleId, @Valid @RequestBody SysRoleUpdateDTO roleUpdateDTO) {
        int updateRow = sysRoleService.updateRole(roleId, roleUpdateDTO);
        if (updateRow <= 0) {
            return R.error("更新角色失败");
        }
        return R.ok("更新角色成功");
    }

}
