package com.zhihao.newretail.rbac.feign;

import com.zhihao.newretail.api.rbac.dto.SysUserApiDTO;
import com.zhihao.newretail.api.rbac.vo.SysUserApiVO;
import com.zhihao.newretail.rbac.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@RestController
public class SysUserFeignClient {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/api/sysUserInfo")
    public SysUserApiVO getSysUserApiVO(@RequestBody SysUserApiDTO userApiDTO) {
        return sysUserService.getSysUserApiVO(userApiDTO.getUsername());
    }

}
