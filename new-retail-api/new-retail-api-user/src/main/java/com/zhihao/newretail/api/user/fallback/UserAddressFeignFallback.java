package com.zhihao.newretail.api.user.fallback;

import com.zhihao.newretail.api.user.feign.UserAddressFeignService;
import com.zhihao.newretail.api.user.vo.UserAddressApiVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserAddressFeignFallback implements UserAddressFeignService {

    @Override
    public List<UserAddressApiVO> listUserAddressApiVOS() {
        return null;
    }

    @Override
    public UserAddressApiVO getUserAddressApiVO(Integer addressId) {
        return null;
    }

}
