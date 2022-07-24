package com.zhihao.newretail.user.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.aspect.RequiresLoginAspect;
import com.zhihao.newretail.user.pojo.vo.UserAddressVO;
import com.zhihao.newretail.user.service.AddressService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @RequiresLogin
    @GetMapping("/listAddresses")
    public R list() {
        Integer userId = RequiresLoginAspect.threadLocal.get();
        List<UserAddressVO> listUserAddressVOs = addressService.listUserAddressVOs(userId);
        RequiresLoginAspect.threadLocal.remove();

        if (CollectionUtils.isEmpty(listUserAddressVOs)) {
            return R.error(HttpStatus.SC_NO_CONTENT, "暂无数据")
                    .put("data", listUserAddressVOs);
        }
        return R.ok().put("data", listUserAddressVOs);
    }

}
