package com.zhihao.newretail.order.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.order.form.OrderCreateForm;
import com.zhihao.newretail.order.pojo.vo.OrderSubmitVO;
import com.zhihao.newretail.order.pojo.vo.OrderVO;
import com.zhihao.newretail.order.service.OrderService;
import com.zhihao.newretail.security.UserLoginContext;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequiresLogin
    @GetMapping("/order/submit")
    public R orderSubmit() throws ExecutionException, InterruptedException {
        OrderSubmitVO orderSubmitVO = orderService.getOrderSubmitVO();
        return R.ok().put("data", orderSubmitVO);
    }

    @RequiresLogin
    @PostMapping("/order")
    public R orderConfirm(@Valid @RequestBody OrderCreateForm form) throws ExecutionException, InterruptedException {
//        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
//        OrderVO orderVO = orderService.insertOrder(userId, form);
//        UserLoginContext.clean();
//
//        return R.ok().put("data", orderVO);
        return null;
    }

    @RequiresLogin
    @GetMapping("/order/{orderId}")
    public R orderDetail(@PathVariable Long orderId) throws ExecutionException, InterruptedException {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        OrderVO orderVO = orderService.getOrderVO(userId, orderId);
        UserLoginContext.clean();

        return R.ok().put("data", orderVO);
    }

}
