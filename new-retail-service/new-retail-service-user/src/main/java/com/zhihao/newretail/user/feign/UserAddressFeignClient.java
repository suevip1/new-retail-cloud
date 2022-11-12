package com.zhihao.newretail.user.feign;

import com.zhihao.newretail.api.user.vo.UserAddressApiVO;
import com.zhihao.newretail.security.context.UserLoginContext;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.user.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/feign")
public class UserAddressFeignClient {

    @Autowired
    private UserAddressService userAddressService;

    @RequiresLogin
    @GetMapping("/address/list")
    public List<UserAddressApiVO> listUserAddressApiVOS() {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        List<UserAddressApiVO> userAddressApiVOList = userAddressService.listUserAddressApiVOS(userId);
        UserLoginContext.clean();
        return userAddressApiVOList;
    }

    @GetMapping("/address/{addressId}")
    public UserAddressApiVO getUserAddressApiVO(@PathVariable Integer addressId) {
        return userAddressService.getUserAddressApiVO(addressId);
    }

    @PostMapping("/address/list")
    List<UserAddressApiVO> listUserAddressApiVOS(@RequestBody Set<Integer> userIdSet) {
        return userAddressService.listUserAddressApiVOS(userIdSet);
    }

}
