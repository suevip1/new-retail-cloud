package com.zhihao.newretail.order.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.order.form.OrderSubmitForm;
import com.zhihao.newretail.order.pojo.vo.OrderCreateVO;
import com.zhihao.newretail.order.pojo.vo.OrderVO;
import com.zhihao.newretail.order.service.OrderService;
import com.zhihao.newretail.security.context.UserLoginContext;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequiresLogin
    @GetMapping("/order/create")
    public R orderCreate() {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        OrderCreateVO orderCreateVO = orderService.getOrderCreateVO(userId);
        UserLoginContext.clean();

        return R.ok().put("data", orderCreateVO);
    }

    @RequiresLogin
    @PostMapping("/order")
    public R orderSubmit(@Valid @RequestBody OrderSubmitForm form) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        String uuid = UserLoginContext.getUserLoginInfo().getUuid();
        Long orderNo = orderService.insertOrder(userId, uuid, form);
        UserLoginContext.clean();

        return R.ok().put("data", orderNo);
    }

    @RequiresLogin
    @GetMapping("/order/{orderId}")
    public R orderDetail(@PathVariable Long orderId) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        OrderVO orderVO = orderService.getOrderVO(userId, orderId);
        UserLoginContext.clean();

        return R.ok().put("data", orderVO);
    }

    @RequiresLogin
    @PutMapping("/order/{orderId}")
    public R orderCancel(@PathVariable Long orderId) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        orderService.updateOrder(userId, orderId);
        UserLoginContext.clean();

        return R.ok();
    }

    @RequiresLogin
    @GetMapping("/order/list")
    public R orderList(@RequestParam(name = "status", required = false) Integer status) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        List<OrderVO> orderVOList = orderService.listOrderVOS(userId, status);
        UserLoginContext.clean();

        return R.ok().put("data", orderVOList);
    }

}
