package com.zhihao.newretail.user.controller;

import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.security.context.UserLoginContext;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.user.form.UserAddressAddForm;
import com.zhihao.newretail.user.form.UserAddressUpdateForm;
import com.zhihao.newretail.user.vo.UserAddressVO;
import com.zhihao.newretail.user.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    /*
    * 用户收货地址列表
    * */
    @RequiresLogin
    @GetMapping("/address/list")
    public R getAddresses() {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        List<UserAddressVO> userAddressVOList = userAddressService.listUserAddressVOS(userId);
        UserLoginContext.clean();
        return R.ok().put("data", userAddressVOList);
    }

    /*
    * 用户收货地址信息
    * */
    @RequiresLogin
    @GetMapping("/address/{addressId}")
    public R getAddress(@PathVariable Integer addressId) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        UserAddressVO userAddressVO = userAddressService.getUserAddressVO(userId, addressId);
        UserLoginContext.clean();
        return R.ok().put("data", userAddressVO);
    }

    /*
    * 新增用户收货地址
    * */
    @RequiresLogin
    @PostMapping("/address")
    public R addAddress(@Valid @RequestBody UserAddressAddForm form) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        int insertUserAddressRow = userAddressService.insertUserAddress(userId, form);
        UserLoginContext.clean();
        if (insertUserAddressRow >= 1) {
            return R.ok("新增收货地址成功");
        }
        throw new ServiceException("新增收货地址失败");
    }

    /*
    * 修改用户收货地址
    * */
    @RequiresLogin
    @PutMapping("/address/{addressId}")
    public R updateAddress(@PathVariable Integer addressId, @Valid @RequestBody UserAddressUpdateForm form) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        int updateUserAddressRow = userAddressService.updateUserAddress(userId, addressId, form);
        UserLoginContext.clean();
        if (updateUserAddressRow >= 1) {
            return R.ok("修改收货地址成功");
        }
        throw new ServiceException("修改收货地址失败");
    }

    /*
    * 删除用户收货地址
    * */
    @RequiresLogin
    @DeleteMapping("/address/{addressId}")
    public R deleteAddress(@PathVariable Integer addressId) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        int deleteUserAddressRow = userAddressService.deleteUserAddress(userId, addressId);
        UserLoginContext.clean();
        if (deleteUserAddressRow >= 1) {
            return R.ok("删除收货地址成功");
        }
        throw new ServiceException("删除收货地址失败");
    }

}
