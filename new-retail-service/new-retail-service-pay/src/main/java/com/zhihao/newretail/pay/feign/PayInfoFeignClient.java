package com.zhihao.newretail.pay.feign;

import com.zhihao.newretail.api.pay.vo.PayInfoApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.pay.service.PayInfoService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign")
public class PayInfoFeignClient {

    @Autowired
    private PayInfoService payInfoService;

    @RequiresLogin
    @GetMapping("/pay-info/list")
    PageUtil<PayInfoApiVO> listPayInfoApiVOS(@RequestParam(required = false) Long orderId,
                                             @RequestParam(required = false) Integer userId,
                                             @RequestParam(required = false) Integer payPlatform,
                                             @RequestParam(required = false) Integer status,
                                             @RequestParam(required = false) Integer platformNumber,
                                             @RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageUtil<PayInfoApiVO> pageData = payInfoService.listPayInfoApiVOS(orderId, userId, payPlatform, status, platformNumber, pageNum, pageSize);
        UserLoginContext.sysClean();
        return pageData;
    }

}
