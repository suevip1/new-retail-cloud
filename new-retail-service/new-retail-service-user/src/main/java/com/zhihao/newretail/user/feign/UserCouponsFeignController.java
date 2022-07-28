package com.zhihao.newretail.user.feign;

import com.zhihao.newretail.api.user.feign.UserCouponsFeignService;
import com.zhihao.newretail.api.user.vo.UserCouponsApiVO;
import com.zhihao.newretail.security.UserLoginContext;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.user.service.UserCouponsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserCouponsFeignController implements UserCouponsFeignService {

    @Autowired
    private UserCouponsService userCouponsService;

    @RequiresLogin
    @Override
    public List<UserCouponsApiVO> listUserCouponsApiVOs() {
        Integer userId = UserLoginContext.getUserLoginInfo();
        List<UserCouponsApiVO> userCouponsApiVOList = userCouponsService.listUserCouponsApiVOs(userId);
        UserLoginContext.clean();
        return userCouponsApiVOList;
    }

}
