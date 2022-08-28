package com.zhihao.newretail.api.order.feign;

import com.zhihao.newretail.api.order.fallback.OrderFeignFallback;
import com.zhihao.newretail.api.order.vo.OrderApiVO;
import com.zhihao.newretail.api.order.vo.OrderPayInfoApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Primary
@FeignClient(name = "new-retail-order", path = "/order", fallback = OrderFeignFallback.class)
public interface OrderFeignService {

    @GetMapping("/api/order/{orderId}")
    OrderPayInfoApiVO getOrderPayInfoApiVO(@PathVariable Long orderId);

    @GetMapping("/api/order")
    PageUtil<OrderApiVO> listOrderApiVOSByPage(@RequestParam(required = false) Long orderNo,
                                               @RequestParam(required = false) Integer userId,
                                               @RequestParam(required = false) Integer status,
                                               @RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize);

}
