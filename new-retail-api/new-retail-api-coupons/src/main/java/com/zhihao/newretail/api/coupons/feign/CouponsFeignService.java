package com.zhihao.newretail.api.coupons.feign;

import com.zhihao.newretail.api.coupons.dto.CouponsBatchApiDTO;
import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "new-retail-coupons", path = "/coupons")
public interface CouponsFeignService {

    @GetMapping("/api/coupons/{couponsId}")
    CouponsApiVO getCouponsApiVO(@PathVariable Integer couponsId);

    @PostMapping("/api/coupons/list")
    List<CouponsApiVO> listCouponsApiVOs(@RequestBody CouponsBatchApiDTO couponsBatchApiDTO);

}
