package com.zhihao.newretail.order.feign;

import com.zhihao.newretail.api.order.vo.OrderApiVO;
import com.zhihao.newretail.api.order.vo.OrderPayInfoApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.order.service.OrderService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequiresLogin
    @GetMapping("/api/order")
    PageUtil<OrderApiVO> listOrderApiVOSByPage(@RequestParam(required = false) Long orderNo,
                                               @RequestParam(required = false) Integer userId,
                                               @RequestParam(required = false) Integer status,
                                               @RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize) {
        PageUtil<OrderApiVO> pageData = orderService.listOrderApiVOS(orderNo, userId, status, pageNum, pageSize);
        UserLoginContext.sysClean();
        return pageData;
    }

}
