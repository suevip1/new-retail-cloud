package com.zhihao.newretail.order.service;

import com.zhihao.newretail.order.pojo.vo.OrderSubmitVO;

import java.util.concurrent.ExecutionException;

public interface OrderService {

    /*
    * 订单提交页
    * */
    OrderSubmitVO getOrderSubmitVO() throws ExecutionException, InterruptedException;

}
