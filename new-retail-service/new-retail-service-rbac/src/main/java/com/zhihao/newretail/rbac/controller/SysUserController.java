package com.zhihao.newretail.rbac.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.rbac.pojo.dto.SysUserAddDTO;
import com.zhihao.newretail.rbac.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@RestController
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/user")
    public R userCreate(@Valid @RequestBody SysUserAddDTO userAddDTO) {
        int insertRow = sysUserService.insertSysUser(userAddDTO);
        if (insertRow <= 0) {
            return R.error("创建失败");
        }
        return R.ok("创建成功");
    }

}
