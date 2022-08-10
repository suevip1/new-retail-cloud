package com.zhihao.newretail.order.service;

import com.zhihao.newretail.api.order.vo.OrderApiVO;
import com.zhihao.newretail.order.form.OrderSubmitForm;
import com.zhihao.newretail.order.pojo.Order;
import com.zhihao.newretail.order.pojo.vo.OrderCreateVO;
import com.zhihao.newretail.order.pojo.vo.OrderVO;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface OrderService {

    /*
    * 订单提交页
    * */
    OrderCreateVO getOrderCreateVO(Integer userId) throws ExecutionException, InterruptedException;

    /*
    * 创建新订单
    * */
    Long insertOrder(Integer userId, String uuid, OrderSubmitForm form) throws ExecutionException, InterruptedException;

    /*
    * 获取订单详情
    * */
    OrderVO getOrderVO(Integer userId, Long orderId) throws ExecutionException, InterruptedException;

    /*
     * 获取订单列表
     * */
    List<OrderVO> listOrderVOs(Integer userId, Integer status);

    /*
    * 用户取消订单
    * */
    void updateOrder(Integer userId, Long orderId);

    /*
     * 更新订单
     * */
    void updateOrder(Order order);

    /*
     * 获取订单
     * */
    Order getOrder(Long orderId);

    /*
    * 获取订单信息
    * */
    OrderApiVO getOrderApiVO(Long orderId);

}
