package com.zhihao.newretail.order.feign;

import com.zhihao.newretail.api.order.vo.OrderPayInfoApiVO;
import com.zhihao.newretail.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@RestController
public class OrderFeignClient {

    @Autowired
    private OrderService orderService;

    @GetMapping("/api/order/{orderId}")
    public OrderPayInfoApiVO getOrderPayInfoApiVO(@PathVariable Long orderId) {
        return orderService.getOrderPayInfoApiVO(orderId);
    }

}
