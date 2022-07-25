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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @RequiresLogin
    @GetMapping("/listAddresses")
    public R listUserAddressVOs() {
        Integer userId = UserLoginContext.getUserLoginInfo();
        List<UserAddressVO> listUserAddressVOs = userAddressService.listUserAddressVOs(userId);
        UserLoginContext.clean();

        if (CollectionUtils.isEmpty(listUserAddressVOs)) {
            return R.error(HttpStatus.SC_NO_CONTENT, "暂无数据")
                    .put("data", listUserAddressVOs);
        }
        return R.ok().put("data", listUserAddressVOs);
    }

    @RequiresLogin
    @GetMapping("/address/{addressId}")
    public R getUserAddressVO(@PathVariable Integer addressId) {
        Integer userId = UserLoginContext.getUserLoginInfo();
        UserAddressVO userAddressVO = userAddressService.getUserAddressVO(userId, addressId);
        UserLoginContext.clean();

        return R.ok().put("data", userAddressVO);
    }

    @RequiresLogin
    @PostMapping("/address")
    public R insertUserAddress(@Valid @RequestBody UserAddressAddForm form) {
        Integer userId = UserLoginContext.getUserLoginInfo();
        userAddressService.insertUserAddress(userId, form);
        UserLoginContext.clean();

        return R.ok("新增成功");
    }

    @RequiresLogin
    @PutMapping("/address/{addressId}")
    public R updateUserAddress(@PathVariable Integer addressId,
                               @Valid @RequestBody UserAddressUpdateForm form) {
        Integer userId = UserLoginContext.getUserLoginInfo();
        userAddressService.updateUserAddress(userId, addressId, form);
        UserLoginContext.clean();

        return R.ok("更新成功");
    }

    @RequiresLogin
    @DeleteMapping("/address/{addressId}")
    public R deleteUserAddress(@PathVariable Integer addressId) {
        Integer userId = UserLoginContext.getUserLoginInfo();
        userAddressService.deleteUserAddress(userId, addressId);
        UserLoginContext.clean();
        return R.ok("删除成功");
    }

}
