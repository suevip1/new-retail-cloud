package com.zhihao.newretail.api.coupons.feign;

import com.zhihao.newretail.api.coupons.dto.CouponsAddApiDTO;
import com.zhihao.newretail.api.coupons.dto.CouponsUpdateApiDTO;
import com.zhihao.newretail.api.coupons.fallback.CouponsFeignFallback;
import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Primary
@FeignClient(name = "new-retail-coupons", path = "/new-retail-coupons", fallback = CouponsFeignFallback.class)
public interface CouponsFeignService {

    @GetMapping("/feign/coupons/{couponsId}")
    CouponsApiVO getCouponsApiVO(@PathVariable Integer couponsId);

    @GetMapping("/feign/coupons/list")
    PageUtil<CouponsApiVO> listCouponsApiVOS(@RequestParam(required = false) Integer saleable,
                                             @RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize);

    @PostMapping("/feign/coupons/list")
    List<CouponsApiVO> listCouponsApiVOS(@RequestBody Set<Integer> couponsIdSet);

    @PostMapping("/feign/coupons")
    Integer insertCoupons(@RequestBody CouponsAddApiDTO couponsAddApiDTO);

    @PutMapping("/feign/coupons/{couponsId}")
    Integer updateCoupons(@PathVariable Integer couponsId, @RequestBody CouponsUpdateApiDTO couponsUpdateApiDTO);

    @DeleteMapping("/feign/coupons/{couponsId}")
    Integer deleteCoupons(@PathVariable Integer couponsId);

}
