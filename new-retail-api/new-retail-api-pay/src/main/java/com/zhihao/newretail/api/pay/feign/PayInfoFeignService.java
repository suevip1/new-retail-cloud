package com.zhihao.newretail.api.pay.feign;

import com.zhihao.newretail.api.pay.fallback.PayInfoFeignFallback;
import com.zhihao.newretail.api.pay.vo.PayInfoApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Primary
@FeignClient(name = "new-retail-pay", path = "/pay", fallback = PayInfoFeignFallback.class)
public interface PayInfoFeignService {

    @GetMapping("/feign/payInfo/list")
    PageUtil<PayInfoApiVO> listPayInfoApiVOS(@RequestParam(required = false) Long orderId,
                                             @RequestParam(required = false) Integer userId,
                                             @RequestParam(required = false) Integer payPlatform,
                                             @RequestParam(required = false) Integer status,
                                             @RequestParam(required = false) Integer platformNumber,
                                             @RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize);

}
