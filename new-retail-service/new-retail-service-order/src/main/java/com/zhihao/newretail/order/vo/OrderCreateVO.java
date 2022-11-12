package com.zhihao.newretail.order.vo;

import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.api.user.vo.UserAddressApiVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderCreateVO {

    private List<UserAddressApiVO> userAddressApiVOList;

    private List<OrderItemCreateVO> orderItemCreateVOList;

    private List<CouponsApiVO> couponsApiVOList;

    private BigDecimal totalPrice;

    private String orderToken;

}
