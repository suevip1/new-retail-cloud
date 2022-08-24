package com.zhihao.newretail.api.user.fallback;

import com.zhihao.newretail.api.user.dto.UserCouponsApiDTO;
import com.zhihao.newretail.api.user.feign.UserCouponsFeignService;
import com.zhihao.newretail.api.user.vo.UserCouponsApiVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCouponsFeignFallback implements UserCouponsFeignService {

    @Override
    public List<UserCouponsApiVO> listUserCouponsApiVOS() {
        return null;
    }

    @Override
    public int consumeCoupons(UserCouponsApiDTO userCouponsApiDTO) {
        return 0;
    }

}
