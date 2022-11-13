package com.zhihao.newretail.admin.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.admin.form.SysUserForm;
import com.zhihao.newretail.admin.vo.SysUserVO;
import com.zhihao.newretail.admin.service.SysUserService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/user/list")
    public R userList() {
        List<SysUserVO> sysUserVOList = sysUserService.listSysUserVOS();
        if (!CollectionUtils.isEmpty(sysUserVOList)) {
            return R.ok().put("data", sysUserVOList);
        }
        return R.error(HttpStatus.SC_NO_CONTENT, "暂无数据").put("data", sysUserVOList);
    }

    @PostMapping("/user")
    public R userCreate(@Valid @RequestBody SysUserForm form) {
        int insertRow = sysUserService.insertSysUser(form);
        if (insertRow >= 1) {
            return R.ok("创建成功");
        }
        return R.error("创建失败");
    }

    @PutMapping("/user/{userId}")
    public R userUpdate(@PathVariable Integer userId, @Valid @RequestBody SysUserForm form) {
        int updateRow = sysUserService.updateSysUser(userId, form);
        if (updateRow >= 1) {
            return R.ok("修改成功");
        }
        return R.error("修改失败");
    }

    @DeleteMapping("/user/{userId}")
    public R userDelete(@PathVariable Integer userId) {
        int deleteRow = sysUserService.deleteSysUser(userId);
        if (deleteRow >= 1) {
            return R.ok("删除成功");
        }
        return R.error("删除失败");
    }

}
