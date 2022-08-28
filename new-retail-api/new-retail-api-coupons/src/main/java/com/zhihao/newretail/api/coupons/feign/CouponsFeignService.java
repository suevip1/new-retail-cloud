package com.zhihao.newretail.api.coupons.feign;

import com.zhihao.newretail.api.coupons.dto.CouponsAddApiDTO;
import com.zhihao.newretail.api.coupons.dto.CouponsUpdateApiDTO;
import com.zhihao.newretail.api.coupons.fallback.CouponsFeignFallback;
import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Primary
@FeignClient(name = "new-retail-coupons", path = "/coupons", fallback = CouponsFeignFallback.class)
public interface CouponsFeignService {

    @GetMapping("/api/coupons/{couponsId}")
    CouponsApiVO getCouponsApiVO(@PathVariable Integer couponsId);

    @GetMapping("/api/coupons/list")
    PageUtil<CouponsApiVO> listCouponsApiVOS(@RequestParam(required = false) Integer saleable,
                                             @RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize);

    @PostMapping("/api/coupons/list")
    List<CouponsApiVO> listCouponsApiVOS(@RequestBody Set<Integer> couponsIdSet);

    @PostMapping("/api/coupons")
    Integer insertCoupons(@RequestBody CouponsAddApiDTO couponsAddApiDTO);

    @PutMapping("/api/coupons/{couponsId}")
    Integer updateCoupons(@PathVariable Integer couponsId, @RequestBody CouponsUpdateApiDTO couponsUpdateApiDTO);

    @DeleteMapping("/api/coupons/{couponsId}")
    Integer deleteCoupons(@PathVariable Integer couponsId);

}
