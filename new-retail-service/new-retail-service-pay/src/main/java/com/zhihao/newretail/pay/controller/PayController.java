package com.zhihao.newretail.pay.controller;

import com.alipay.api.AlipayApiException;
import com.zhihao.newretail.pay.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Controller
@RequestMapping("/alipay")
public class PayController {

    @Autowired
    private PayService payService;

    @GetMapping("/pc/{orderId}")
    public ModelAndView alipayPC(@PathVariable Long orderId) throws AlipayApiException {
        String body = payService.getBody(orderId);
        Map<String, Object> map = new HashMap<>();
        map.put("body", body);
        return new ModelAndView("alipayPC", map);
    }

    @PostMapping("/notify")
    public void asyncNotify(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, AlipayApiException {
        payService.asyncNotify(request, response);
    }

}
