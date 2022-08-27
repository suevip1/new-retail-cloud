package com.zhihao.newretail.api.order.feign;

import com.zhihao.newretail.api.order.vo.OrderPayInfoApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@FeignClient(name = "new-retail-order", path = "/order")
public interface OrderFeignService {

    @GetMapping("/api/order/{orderId}")
    OrderPayInfoApiVO getOrderPayInfoApiVO(@PathVariable Long orderId);

}
