package com.zhihao.newretail.admin.feign;

import com.zhihao.newretail.api.admin.vo.SysUserApiVO;
import com.zhihao.newretail.admin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/sys/user-info/{username}")
    public SysUserApiVO getSysUserApiVO(@PathVariable String username) {
        return sysUserService.getSysUserApiVO(username);
    }

}
