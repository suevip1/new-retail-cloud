package com.zhihao.newretail.api.order.fallback;

import com.zhihao.newretail.api.order.feign.OrderFeignService;
import com.zhihao.newretail.api.order.vo.OrderPayInfoApiVO;
import org.springframework.stereotype.Component;

@Component
public class OrderFeignFallback implements OrderFeignService {

    @Override
    public OrderPayInfoApiVO getOrderPayInfoApiVO(Long orderId) {
        return null;
    }

}
