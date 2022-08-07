package com.zhihao.newretail.api.user.feign;

import com.zhihao.newretail.api.user.dto.UserCouponsApiDTO;
import com.zhihao.newretail.api.user.vo.UserCouponsApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "new-retail-user", path = "/user")
public interface UserCouponsFeignService {

    @GetMapping("/api/userCoupons/list")
    List<UserCouponsApiVO> listUserCouponsApiVOs();

    @PutMapping("/api/userCoupons")
    int consumeCoupons(@RequestBody UserCouponsApiDTO userCouponsApiDTO);

}
