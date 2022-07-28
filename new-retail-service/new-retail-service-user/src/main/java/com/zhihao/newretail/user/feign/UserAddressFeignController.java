package com.zhihao.newretail.user.feign;

import com.zhihao.newretail.api.user.feign.UserAddressFeignService;
import com.zhihao.newretail.api.user.vo.UserAddressApiVO;
import com.zhihao.newretail.security.UserLoginContext;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.user.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserAddressFeignController implements UserAddressFeignService {

    @Autowired
    private UserAddressService userAddressService;

    @RequiresLogin
    @Override
    public List<UserAddressApiVO> listUserAddressApiVOs() {
        Integer userId = UserLoginContext.getUserLoginInfo();
        List<UserAddressApiVO> userAddressApiVOList = userAddressService.listUserAddressApiVOs(userId);
        UserLoginContext.clean();
        return userAddressApiVOList;
    }

}
