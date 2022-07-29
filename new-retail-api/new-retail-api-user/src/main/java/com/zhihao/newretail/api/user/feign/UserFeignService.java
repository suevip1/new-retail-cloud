package com.zhihao.newretail.api.user.feign;

import com.zhihao.newretail.api.user.dto.UserApiDTO;
import com.zhihao.newretail.api.user.vo.UserApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "new-retail-user", path = "/user")
public interface UserFeignService {

    @PostMapping("/api/getUserInfo")
    UserApiVO getUserInfo(@RequestBody UserApiDTO userApiDTO);

}
