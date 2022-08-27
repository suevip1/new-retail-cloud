package com.zhihao.newretail.order.feign;

import com.zhihao.newretail.api.order.feign.OrderFeignService;
import com.zhihao.newretail.api.order.vo.OrderPayInfoApiVO;
import com.zhihao.newretail.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@RestController
public class OrderFeignClient implements OrderFeignService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderPayInfoApiVO getOrderPayInfoApiVO(Long orderId) {
        return orderService.getOrderPayInfoApiVO(orderId);
    }

}
