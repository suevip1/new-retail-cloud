package com.zhihao.newretail.order.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.order.pojo.vo.OrderSubmitVO;
import com.zhihao.newretail.order.service.OrderService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequiresLogin
    @GetMapping("/order/orderSubmit")
    public R getOrderSubmitVO() throws ExecutionException, InterruptedException {
        OrderSubmitVO orderSubmitVO = orderService.getOrderSubmitVO();
        return R.ok().put("data", orderSubmitVO);
    }

}
