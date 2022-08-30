package com.zhihao.newretail.api.pay.fallback;

import com.zhihao.newretail.api.pay.feign.PayInfoFeignService;
import com.zhihao.newretail.api.pay.vo.PayInfoApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import org.springframework.stereotype.Component;

@Component
public class PayInfoFeignFallback implements PayInfoFeignService {

    @Override
    public PageUtil<PayInfoApiVO> listPayInfoApiVOS(Long orderId, Integer userId, Integer payPlatform,
                                                    Integer status, Integer platformNumber, Integer pageNum, Integer pageSize) {
        return null;
    }

}
