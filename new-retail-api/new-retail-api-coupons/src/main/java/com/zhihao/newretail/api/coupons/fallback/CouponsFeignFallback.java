package com.zhihao.newretail.api.coupons.fallback;

import com.zhihao.newretail.api.coupons.feign.CouponsFeignService;
import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class CouponsFeignFallback implements CouponsFeignService {

    @Override
    public CouponsApiVO getCouponsApiVO(Integer couponsId) {
        return null;
    }

    @Override
    public List<CouponsApiVO> listCouponsApiVOS(Set<Integer> couponsIdSet) {
        return null;
    }

}
