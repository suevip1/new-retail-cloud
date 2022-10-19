package com.zhihao.newretail.api.order.fallback;

import com.zhihao.newretail.api.order.feign.OrderFeignService;
import com.zhihao.newretail.api.order.vo.OrderApiVO;
import com.zhihao.newretail.api.order.vo.OrderPayInfoApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import org.springframework.stereotype.Component;

@Component
public class OrderFeignFallback implements OrderFeignService {

    @Override
    public OrderPayInfoApiVO getOrderPayInfoApiVO(Long orderId) {
        return null;
    }

    @Override
    public OrderApiVO getOrderApiVO(Long orderId) {
        return null;
    }

    @Override
    public PageUtil<OrderApiVO> listOrderApiVOSByPage(Long orderNo, Integer userId, Integer status, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public Integer deliverGoods(Long orderNo) {
        return null;
    }

}
