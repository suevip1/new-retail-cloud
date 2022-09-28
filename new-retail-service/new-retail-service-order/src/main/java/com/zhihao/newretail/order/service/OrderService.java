package com.zhihao.newretail.order.service;

import com.zhihao.newretail.api.order.vo.OrderApiVO;
import com.zhihao.newretail.api.order.vo.OrderPayInfoApiVO;
import com.zhihao.newretail.order.form.OrderSubmitForm;
import com.zhihao.newretail.order.pojo.Order;
import com.zhihao.newretail.order.pojo.vo.OrderCreateVO;
import com.zhihao.newretail.order.pojo.vo.OrderVO;
import com.zhihao.newretail.core.util.PageUtil;

public interface OrderService {

    /*
    * 订单提交页
    * */
    OrderCreateVO getOrderCreateVO(Integer userId);

    /*
    * 创建新订单
    * */
    OrderVO insertOrder(Integer userId, String uuid, OrderSubmitForm form);

    /*
    * 获取订单详情
    * */
    OrderVO getOrderVO(Integer userId, Long orderId);

    /*
     * 获取订单列表
     * */
    PageUtil<OrderVO> listOrderVOS(Integer userId, Integer status, Integer pageNum, Integer pageSize);

    /*
    * 获取订单列表(后台)
    * */
    PageUtil<OrderApiVO> listOrderApiVOS(Long orderNo, Integer userId, Integer status, Integer pageNum, Integer pageSize);

    /*
    * 用户取消订单
    * */
    void updateOrder(Integer userId, Long orderId);

    /*
     * 更新订单
     * */
    void updateOrder(Order order);

    /*
    * 订单确认收货
    * */
    void takeAnOrder(Integer userId, Long orderId);

    /*
    * 更新订单(订单发货)
    * */
    int updateOrder(Long orderNo);

    /*
     * 获取订单
     * */
    Order getOrder(Long orderId);

    /*
    * 获取订单支付信息
    * */
    OrderPayInfoApiVO getOrderPayInfoApiVO(Long orderId);

}
