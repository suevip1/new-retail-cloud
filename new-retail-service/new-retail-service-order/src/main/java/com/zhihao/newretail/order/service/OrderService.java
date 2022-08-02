package com.zhihao.newretail.order.service;

import com.zhihao.newretail.order.form.OrderCreateForm;
import com.zhihao.newretail.order.pojo.vo.OrderSubmitVO;
import com.zhihao.newretail.order.pojo.vo.OrderVO;

import java.util.concurrent.ExecutionException;

public interface OrderService {

    /*
    * 订单提交页
    * */
    OrderSubmitVO getOrderSubmitVO() throws ExecutionException, InterruptedException;

    /*
    * 创建新订单
    * */
    void insertOrder(Integer userId, OrderCreateForm form) throws ExecutionException, InterruptedException;

    /*
    * 获取订单详情
    * */
    OrderVO getOrderVO(Integer userId, Long orderId) throws ExecutionException, InterruptedException;

}
