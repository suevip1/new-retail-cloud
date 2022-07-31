package com.zhihao.newretail.user.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.security.UserLoginContext;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.user.form.UserAddressAddForm;
import com.zhihao.newretail.user.form.UserAddressUpdateForm;
import com.zhihao.newretail.user.pojo.vo.UserAddressVO;
import com.zhihao.newretail.user.service.UserAddressService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/address/list")
    public R listUserAddressVOs() {
        List<UserAddressVO> listUserAddressVOs = userAddressService.listUserAddressVOs();

        if (!CollectionUtils.isEmpty(listUserAddressVOs)) {
            return R.ok().put("data", listUserAddressVOs);
        } else {
            return R.error(HttpStatus.SC_NO_CONTENT, "暂无收货地址").put("data", listUserAddressVOs);
        }
    }

    @GetMapping("/address/{addressId}")
    public R getUserAddressVO(@PathVariable Integer addressId) {
        UserAddressVO userAddressVO = userAddressService.getUserAddressVO(addressId);
        return R.ok().put("data", userAddressVO);
    }

    @PostMapping("/address")
    public R addUserAddress(@Valid @RequestBody UserAddressAddForm form) {
        Integer addUserAddressRow = userAddressService.addUserAddress(form);

        if (!ObjectUtils.isEmpty(addUserAddressRow)) {
            return R.ok("新增收货地址成功");
        } else {
            return R.error("新增收货地址失败");
        }
    }

    @RequiresLogin
    @PutMapping("/address/{addressId}")
    public R updateUserAddress(@PathVariable Integer addressId,
                               @Valid @RequestBody UserAddressUpdateForm form) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        userAddressService.updateUserAddress(userId, addressId, form);
        UserLoginContext.clean();

        return R.ok("更新成功");
    }

    @RequiresLogin
    @DeleteMapping("/address/{addressId}")
    public R deleteUserAddress(@PathVariable Integer addressId) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        userAddressService.deleteUserAddress(userId, addressId);
        UserLoginContext.clean();

        return R.ok("删除成功");
    }

}
