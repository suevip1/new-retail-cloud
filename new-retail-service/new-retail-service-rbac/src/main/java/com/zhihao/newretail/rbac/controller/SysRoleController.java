package com.zhihao.newretail.rbac.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.rbac.pojo.vo.SysRoleVO;
import com.zhihao.newretail.rbac.service.SysRoleService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
