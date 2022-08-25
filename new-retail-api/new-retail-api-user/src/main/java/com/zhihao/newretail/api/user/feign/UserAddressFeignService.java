package com.zhihao.newretail.api.user.feign;

import com.zhihao.newretail.api.user.fallback.UserAddressFeignFallback;
import com.zhihao.newretail.api.user.vo.UserAddressApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Primary
@FeignClient(name = "new-retail-user", path = "/user", fallback = UserAddressFeignFallback.class)
public interface UserAddressFeignService {

    @GetMapping("/api/address/list")
    List<UserAddressApiVO> listUserAddressApiVOS();

    @GetMapping("/api/address/{addressId}")
    UserAddressApiVO getUserAddressApiVO(@PathVariable Integer addressId);

}
