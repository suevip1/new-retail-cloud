package com.zhihao.newretail.api.user.feign;

import com.zhihao.newretail.api.user.vo.UserAddressApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "new-retail-user", path = "/user")
public interface UserAddressFeignService {

    @GetMapping("/address")
    List<UserAddressApiVO> listUserAddressApiVOs();

}
