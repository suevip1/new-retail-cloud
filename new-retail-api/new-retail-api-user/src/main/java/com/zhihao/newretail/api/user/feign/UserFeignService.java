package com.zhihao.newretail.api.user.feign;

import com.zhihao.newretail.api.user.dto.UserApiDTO;
import com.zhihao.newretail.api.user.fallback.UserFeignFallback;
import com.zhihao.newretail.api.user.vo.UserApiVO;
import com.zhihao.newretail.api.user.vo.UserInfoApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@Primary
@FeignClient(name = "new-retail-user", path = "/new-retail-user", fallback = UserFeignFallback.class)
public interface UserFeignService {

    @PostMapping("/feign/user")
    UserApiVO getUserApiVO(@RequestBody UserApiDTO userApiDTO);

    @GetMapping("/feign/info/{userId}")
    UserInfoApiVO getUserInfoApiVO(@PathVariable Integer userId);

    @PostMapping("/feign/info/list")
    List<UserInfoApiVO> listUserInfoApiVOS(@RequestBody Set<Integer> userIdSet);

}
