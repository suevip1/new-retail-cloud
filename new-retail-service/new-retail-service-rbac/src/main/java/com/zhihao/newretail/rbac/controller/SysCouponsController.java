package com.zhihao.newretail.rbac.controller;

import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.rbac.context.SysUserTokenContext;
import com.zhihao.newretail.rbac.form.CouponsForm;
import com.zhihao.newretail.rbac.service.SysCouponsService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class SysCouponsController {

    @Autowired
    private SysCouponsService sysCouponsService;

    @RequiresLogin
    @PostMapping("/coupons")
    public R couponsAdd(@Valid @RequestBody CouponsForm form) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer insertRow = sysCouponsService.insertCoupons(form);
        UserLoginContext.sysClean();
        if (insertRow == null) {
            throw new ServiceException("优惠券服务繁忙");
        }
        if (insertRow <= 0) {
            throw new ServiceException("新增优惠券失败");
        }
        return R.ok("新增优惠券成功");
    }

}
