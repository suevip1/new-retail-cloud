package com.zhihao.newretail.api.admin.feign;

import com.zhihao.newretail.api.admin.dto.SysUserApiDTO;
import com.zhihao.newretail.api.admin.fallback.SysUserFeignFallback;
import com.zhihao.newretail.api.admin.vo.SysUserApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Primary
@FeignClient(name = "new-retail-admin", path = "/new-retail-admin", fallback = SysUserFeignFallback.class)
public interface SysUserFeignService {

    @PostMapping("/feign/sys/user-info")
    SysUserApiVO getSysUserApiVO(@RequestBody SysUserApiDTO userApiDTO);

}
