package com.zhihao.newretail.pay.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradePagePayResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public interface PayService {

    /*
    * 支付宝电脑网页支付
    * */
    String getBody(Long orderId) throws AlipayApiException;

    /*
    * 支付成功异步通知
    * */
    void asyncNotify(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException, AlipayApiException;

}
