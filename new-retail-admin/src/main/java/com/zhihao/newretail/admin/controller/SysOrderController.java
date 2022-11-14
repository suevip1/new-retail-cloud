package com.zhihao.newretail.admin.controller;

import com.zhihao.newretail.admin.form.OrderShippedForm;
import com.zhihao.newretail.api.order.vo.OrderApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.admin.context.SysUserTokenContext;
import com.zhihao.newretail.admin.service.SysOrderService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SysOrderController {

    @Autowired
    private SysOrderService sysOrderService;

    @RequiresLogin
    @GetMapping("/order/{orderNo}")
    public R orderDetail(@PathVariable Long orderNo) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        OrderApiVO orderApiVO = sysOrderService.getOrderApiVO(orderNo);
        UserLoginContext.sysClean();
        if (!ObjectUtils.isEmpty(orderApiVO.getId())) {
            return R.ok().put("data", orderApiVO);
        }
        return R.error(HttpStatus.SC_NOT_FOUND, "订单不存在").put("data", orderApiVO);
    }

    @RequiresLogin
    @GetMapping("/order/list")
    public R orderList(@RequestParam(required = false) Long orderNo,
                       @RequestParam(required = false) Integer userId,
                       @RequestParam(required = false) Integer status,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        if (pageNum != 0 && pageSize != 0) {
            String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
            SysUserTokenContext.setUserToken(userToken);
            PageUtil<OrderApiVO> pageData = sysOrderService.listOrderApiVOSByPage(orderNo, userId, status, pageNum, pageSize);
            UserLoginContext.sysClean();
            if (!CollectionUtils.isEmpty(pageData.getList())) {
                return R.ok().put("data", pageData);
            }
            return R.error(HttpStatus.SC_NO_CONTENT, "暂无数据").put("data", pageData);
        } else {
            UserLoginContext.sysClean();
            throw new ServiceException("分页参数不能为0");
        }
    }

    @RequiresLogin
    @PutMapping("/order")
    public R deliverGoods(@Valid @RequestBody OrderShippedForm form) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer updateRow = sysOrderService.deliverGoods(form);
        UserLoginContext.sysClean();
        if (updateRow != null) {
            if (updateRow >= 1) {
                return R.ok("发货成功");
            }
            return R.error("发货失败");
        }
        throw new ServiceException("订单服务繁忙");
    }

}
