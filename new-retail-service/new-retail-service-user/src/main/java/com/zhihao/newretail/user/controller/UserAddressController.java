package com.zhihao.newretail.user.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.aspect.RequiresLoginAspect;
import com.zhihao.newretail.user.pojo.vo.UserAddressVO;
import com.zhihao.newretail.user.service.UserAddressService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @RequiresLogin
    @GetMapping("/listAddresses")
    public R listUserAddressVOs() {
        Integer userId = RequiresLoginAspect.threadLocal.get();
        List<UserAddressVO> listUserAddressVOs = userAddressService.listUserAddressVOs(userId);
        RequiresLoginAspect.threadLocal.remove();

        if (CollectionUtils.isEmpty(listUserAddressVOs)) {
            return R.error(HttpStatus.SC_NO_CONTENT, "暂无数据")
                    .put("data", listUserAddressVOs);
        }
        return R.ok().put("data", listUserAddressVOs);
    }

    @RequiresLogin
    @GetMapping("/address/{addressId}")
    public R getUserAddressVO(@PathVariable Integer addressId) {
        Integer userId = RequiresLoginAspect.threadLocal.get();
        UserAddressVO userAddressVO = userAddressService.getUserAddressVO(userId, addressId);
        RequiresLoginAspect.threadLocal.remove();

        return R.ok().put("data", userAddressVO);
    }

}
