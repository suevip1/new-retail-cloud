package com.zhihao.newretail.admin.feign;

import com.zhihao.newretail.api.admin.dto.SysUserApiDTO;
import com.zhihao.newretail.api.admin.vo.SysUserApiVO;
import com.zhihao.newretail.admin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@RestController
@RequestMapping("/feign")
public class SysUserFeignClient {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/sysUserInfo")
    public SysUserApiVO getSysUserApiVO(@RequestBody SysUserApiDTO userApiDTO) {
        return sysUserService.getSysUserApiVO(userApiDTO.getUsername());
    }

}
