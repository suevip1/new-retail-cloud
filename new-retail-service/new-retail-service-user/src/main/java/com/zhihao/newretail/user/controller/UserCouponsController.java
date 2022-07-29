package com.zhihao.newretail.user.controller;

import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.security.UserLoginContext;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.user.service.UserCouponsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserCouponsController {

    @Autowired
    private UserCouponsService userCouponsService;

    @RequiresLogin
    @GetMapping("/listUserCouponsVOs")
    public R listUserCouponsVOs() {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        List<CouponsApiVO> couponsApiVOList = userCouponsService.listUserCouponsVOs(userId);
        UserLoginContext.clean();

        return R.ok().put("data", couponsApiVOList);
    }

    @GetMapping("/userCoupons/{couponsId}")
    public R getUserCouponsVO(@PathVariable Integer couponsId) {
        CouponsApiVO userCouponsVO = userCouponsService.getUserCouponsVO(couponsId);
        return R.ok().put("data", userCouponsVO);
    }

}
