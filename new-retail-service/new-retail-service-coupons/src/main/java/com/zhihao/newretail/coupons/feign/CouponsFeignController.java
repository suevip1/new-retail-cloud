package com.zhihao.newretail.coupons.feign;

import com.zhihao.newretail.api.coupons.dto.CouponsBatchApiDTO;
import com.zhihao.newretail.api.coupons.feign.CouponsFeignService;
import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.coupons.service.CouponsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class CouponsFeignController implements CouponsFeignService {

    @Autowired
    private CouponsService couponsService;

    @Override
    public CouponsApiVO getCouponsApiVO(Integer couponsId) {
        return couponsService.getCouponsApiVO(couponsId);
    }

    @Override
    public List<CouponsApiVO> listCouponsApiVOs(CouponsBatchApiDTO couponsBatchApiDTO) {
        Set<Integer> couponsIdSet = couponsBatchApiDTO.getCouponsIdSet();
        return couponsService.listCouponsApiVOs(couponsIdSet);
    }

}
