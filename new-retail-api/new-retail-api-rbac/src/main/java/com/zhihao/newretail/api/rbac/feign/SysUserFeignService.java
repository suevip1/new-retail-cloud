package com.zhihao.newretail.api.rbac.feign;

import com.zhihao.newretail.api.rbac.dto.SysUserApiDTO;
import com.zhihao.newretail.api.rbac.vo.SysUserApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@FeignClient(name = "new-retail-rbac", path = "/rbac")
public interface SysUserFeignService {

    @PostMapping("/api/sysUserInfo")
    SysUserApiVO getSysUserApiVO(@RequestBody SysUserApiDTO userApiDTO);

}
