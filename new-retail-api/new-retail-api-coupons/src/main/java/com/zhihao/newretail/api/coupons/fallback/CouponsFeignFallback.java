package com.zhihao.newretail.api.coupons.fallback;

import com.zhihao.newretail.api.coupons.dto.CouponsAddApiDTO;
import com.zhihao.newretail.api.coupons.dto.CouponsUpdateApiDTO;
import com.zhihao.newretail.api.coupons.feign.CouponsFeignService;
import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.core.util.PageUtil;
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
    public PageUtil<CouponsApiVO> listCouponsApiVOS(Integer saleable, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public List<CouponsApiVO> listCouponsApiVOS(Set<Integer> couponsIdSet) {
        return null;
    }

    @Override
    public Integer insertCoupons(CouponsAddApiDTO couponsAddApiDTO) {
        return null;
    }

    @Override
    public Integer updateCoupons(Integer couponsId, CouponsUpdateApiDTO couponsUpdateApiDTO) {
        return null;
    }

}
