package com.zhihao.newretail.order.service;

import com.zhihao.newretail.order.pojo.vo.OrderSubmitVO;

import java.util.concurrent.ExecutionException;

public interface OrderService {

    OrderSubmitVO getOrderSubmitVO() throws ExecutionException, InterruptedException;

}
