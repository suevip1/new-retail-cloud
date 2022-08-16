package com.zhihao.newretail.rbac.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.rbac.pojo.dto.SysUserAddDTO;
import com.zhihao.newretail.rbac.pojo.dto.SysUserUpdateDTO;
import com.zhihao.newretail.rbac.pojo.vo.SysUserVO;
import com.zhihao.newretail.rbac.service.SysUserService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
        List<SysUserVO> sysUserVOList = sysUserService.listSysUserVOs();
        if (CollectionUtils.isEmpty(sysUserVOList)) {
            return R.error(HttpStatus.SC_NO_CONTENT, "暂无数据").put("data", sysUserVOList);
        }
        return R.ok().put("data", sysUserVOList);
    }

    @PostMapping("/user")
    public R userCreate(@Valid @RequestBody SysUserAddDTO userAddDTO) {
        int insertRow = sysUserService.insertSysUser(userAddDTO);
        if (insertRow <= 0) {
            return R.error("创建失败");
        }
        return R.ok("创建成功");
    }

    @PutMapping("/user/{userId}")
    public R userUpdate(@PathVariable Integer userId, @Valid @RequestBody SysUserUpdateDTO userUpdateDTO) {
        int updateRow = sysUserService.updateSysUser(userId, userUpdateDTO);
        if (updateRow <= 0) {
            return R.error("修改失败");
        }
        return R.ok("修改成功");
    }

    @DeleteMapping("/user/{userId}")
    public R userDelete(@PathVariable Integer userId) {
        int deleteRow = sysUserService.deleteSysUser(userId);
        if (deleteRow <= 0) {
            return R.error("删除失败");
        }
        return R.ok("删除成功");
    }

}
