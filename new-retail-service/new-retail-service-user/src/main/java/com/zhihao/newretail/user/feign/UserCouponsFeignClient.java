package com.zhihao.newretail.user.feign;

import com.zhihao.newretail.api.user.dto.UserCouponsApiDTO;
import com.zhihao.newretail.api.user.vo.UserCouponsApiVO;
import com.zhihao.newretail.security.context.UserLoginContext;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.user.service.UserCouponsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feign")
public class UserCouponsFeignClient {

    @Autowired
    private UserCouponsService userCouponsService;

    @RequiresLogin
    @GetMapping("/userCoupons/list")
    public List<UserCouponsApiVO> listUserCouponsApiVOs() {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        List<UserCouponsApiVO> userCouponsApiVOList = userCouponsService.listUserCouponsApiVOS(userId);
        UserLoginContext.clean();
        return userCouponsApiVOList;
    }

    @PutMapping("/userCoupons")
    public int consumeCoupons(@RequestBody UserCouponsApiDTO userCouponsApiDTO) {
        return userCouponsService.updateUserCoupons(userCouponsApiDTO);
    }

}
