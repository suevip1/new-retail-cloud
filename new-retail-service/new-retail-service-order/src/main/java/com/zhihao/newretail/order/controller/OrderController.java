package com.zhihao.newretail.order.controller;

import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.order.form.OrderSubmitForm;
import com.zhihao.newretail.order.vo.OrderCreateVO;
import com.zhihao.newretail.order.vo.OrderVO;
import com.zhihao.newretail.order.service.OrderService;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.security.context.UserLoginContext;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
        if (!ObjectUtils.isEmpty(orderVO.getId())) {
            return R.ok().put("data", orderVO);
        } else {
            return R.ok("订单不存在").put("data", orderVO);
        }
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
    @PutMapping("/order/receipt/{orderId}")
    public R orderConfirmReceipt(@PathVariable Long orderId) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        orderService.takeAnOrder(userId, orderId);
        UserLoginContext.clean();
        return R.ok();
    }

    @RequiresLogin
    @GetMapping("/order/list")
    public R orderList(@RequestParam(required = false) Integer status,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {
        if (pageNum != 0 && pageSize != 0) {
            Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
            PageUtil<OrderVO> pageData = orderService.listOrderVOS(userId, status, pageNum, pageSize);
            UserLoginContext.clean();
            if (!CollectionUtils.isEmpty(pageData.getList())) {
                return R.ok().put("data", pageData);
            } else {
                return R.ok("暂无订单").put("data", pageData);
            }
        } else {
            UserLoginContext.clean();
            throw new ServiceException("分页参数不能为0");
        }
    }

}
