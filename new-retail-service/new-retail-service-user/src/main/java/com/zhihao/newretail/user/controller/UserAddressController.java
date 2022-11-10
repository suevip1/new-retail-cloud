package com.zhihao.newretail.user.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.security.context.UserLoginContext;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.user.form.UserAddressAddForm;
import com.zhihao.newretail.user.form.UserAddressUpdateForm;
import com.zhihao.newretail.user.vo.UserAddressVO;
import com.zhihao.newretail.user.service.UserAddressService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @RequiresLogin
    @GetMapping("/address/list")
    public R getAddresses() {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        List<UserAddressVO> listUserAddressVOs = userAddressService.listUserAddressVOS(userId);
        UserLoginContext.clean();

        if (!CollectionUtils.isEmpty(listUserAddressVOs)) {
            return R.ok().put("data", listUserAddressVOs);
        }
        return R.error(HttpStatus.SC_NO_CONTENT, "暂无收货地址").put("data", listUserAddressVOs);
    }

    @RequiresLogin
    @GetMapping("/address/{addressId}")
    public R getAddress(@PathVariable Integer addressId) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        UserAddressVO userAddressVO = userAddressService.getUserAddressVO(userId, addressId);
        UserLoginContext.clean();

        return R.ok().put("data", userAddressVO);
    }

    @RequiresLogin
    @PostMapping("/address")
    public R addAddress(@Valid @RequestBody UserAddressAddForm form) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        userAddressService.insertUserAddress(userId, form);
        UserLoginContext.clean();

        return R.ok("新增收货地址成功");
    }

    @RequiresLogin
    @PutMapping("/address/{addressId}")
    public R updateAddress(@PathVariable Integer addressId, @Valid @RequestBody UserAddressUpdateForm form) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        userAddressService.updateUserAddress(userId, addressId, form);
        UserLoginContext.clean();

        return R.ok("更新收货地址成功");
    }

    @RequiresLogin
    @DeleteMapping("/address/{addressId}")
    public R deleteAddress(@PathVariable Integer addressId) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        userAddressService.deleteUserAddress(userId, addressId);
        UserLoginContext.clean();

        return R.ok("删除收货地址成功");
    }

}
