package com.zhihao.newretail.api.user.feign;

import com.zhihao.newretail.api.user.dto.UserApiDTO;
import com.zhihao.newretail.api.user.vo.UserApiVO;
import com.zhihao.newretail.api.user.vo.UserInfoApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@FeignClient(name = "new-retail-user", path = "/user")
public interface UserFeignService {

    @PostMapping("/api/userInfo")
    UserApiVO getUserApiVO(@RequestBody UserApiDTO userApiDTO);

    @PostMapping("/api/userInfo/list")
    List<UserInfoApiVO> listUserInfoApiVOS(@RequestBody Set<Integer> userIdSet);

}
