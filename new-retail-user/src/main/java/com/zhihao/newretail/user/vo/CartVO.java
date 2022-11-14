package com.zhihao.newretail.user.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartVO {

    private Boolean selectedAll;

    private Integer totalQuantity;

    private BigDecimal cartTotalPrice;

    private List<CartProductVO> cartProductVOList;

}
