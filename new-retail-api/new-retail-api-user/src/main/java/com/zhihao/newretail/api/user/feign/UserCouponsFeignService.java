package com.zhihao.newretail.api.user.feign;

import com.zhihao.newretail.api.user.vo.UserCouponsApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "new-retail-user", path = "/user")
public interface UserCouponsFeignService {

    @GetMapping("/api/listUserCoupons")
    List<UserCouponsApiVO> listUserCouponsApiVOs();

}
