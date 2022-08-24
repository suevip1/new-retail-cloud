package com.zhihao.newretail.api.coupons.feign;

import com.zhihao.newretail.api.coupons.fallback.CouponsFeignFallback;
import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@Primary
@FeignClient(name = "new-retail-coupons", path = "/coupons", fallback = CouponsFeignFallback.class)
public interface CouponsFeignService {

    @GetMapping("/api/coupons/{couponsId}")
    CouponsApiVO getCouponsApiVO(@PathVariable Integer couponsId);

    @PostMapping("/api/coupons/list")
    List<CouponsApiVO> listCouponsApiVOS(@RequestBody Set<Integer> couponsIdSet);

}
