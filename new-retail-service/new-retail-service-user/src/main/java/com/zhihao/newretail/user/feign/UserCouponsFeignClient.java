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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserCouponsFeignClient {

    @Autowired
    private UserCouponsService userCouponsService;

    @RequiresLogin
    @GetMapping("/api/userCoupons/list")
    public List<UserCouponsApiVO> listUserCouponsApiVOs() {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        List<UserCouponsApiVO> userCouponsApiVOList = userCouponsService.listUserCouponsApiVOS(userId);
        UserLoginContext.clean();
        return userCouponsApiVOList;
    }

    @PutMapping("/api/userCoupons")
    public int consumeCoupons(@RequestBody UserCouponsApiDTO userCouponsApiDTO) {
        return userCouponsService.updateUserCoupons(userCouponsApiDTO);
    }

}
