package com.zhihao.newretail.user.feign;

import com.zhihao.newretail.api.user.dto.UserCouponsApiDTO;
import com.zhihao.newretail.api.user.vo.UserCouponsApiVO;
import com.zhihao.newretail.security.context.UserLoginContext;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.user.service.UserCouponsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feign")
public class UserCouponsFeignClient {

    @Autowired
    private UserCouponsService userCouponsService;

    @RequiresLogin
    @GetMapping("/user-coupons/list")
    public List<UserCouponsApiVO> listUserCouponsApiVOS() {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        List<UserCouponsApiVO> userCouponsApiVOList = userCouponsService.listUserCouponsApiVOS(userId);
        UserLoginContext.clean();
        return userCouponsApiVOList;
    }

    @PutMapping("/user-coupons")
    public int consumeCoupons(@RequestBody UserCouponsApiDTO userCouponsApiDTO) {
        return userCouponsService.updateUserCoupons(userCouponsApiDTO);
    }

}
