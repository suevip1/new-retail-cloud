package com.zhihao.newretail.api.order.feign;

import com.zhihao.newretail.api.order.dto.OrderLogisticsInfoAddApiDTO;
import com.zhihao.newretail.api.order.fallback.OrderFeignFallback;
import com.zhihao.newretail.api.order.vo.OrderApiVO;
import com.zhihao.newretail.api.order.vo.OrderPayInfoApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Primary
@FeignClient(name = "new-retail-order", path = "/new-retail-order", fallback = OrderFeignFallback.class)
public interface OrderFeignService {

    /*
    * 获取需要支付的订单信息
    * */
    @GetMapping("/feign/order-pay/{orderId}")
    OrderPayInfoApiVO getOrderPayInfoApiVO(@PathVariable Long orderId);

    /*
    * 获取订单详情
    * */
    @GetMapping("/feign/order/{orderId}")
    OrderApiVO getOrderApiVO(@PathVariable Long orderId);

    /*
    * 获取订单列表
    * */
    @GetMapping("/feign/order")
    PageUtil<OrderApiVO> listOrderApiVOSByPage(@RequestParam(required = false) Long orderNo,
                                               @RequestParam(required = false) Integer userId,
                                               @RequestParam(required = false) Integer status,
                                               @RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize);

    /*
    * 订单发货
    * */
    @PutMapping("/feign/order/{orderNo}")
    Integer deliverGoods(@PathVariable Long orderNo, @RequestBody OrderLogisticsInfoAddApiDTO orderLogisticsInfoAddApiDTO);

}
