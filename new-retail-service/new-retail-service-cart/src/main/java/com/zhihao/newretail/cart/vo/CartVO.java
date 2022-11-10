package com.zhihao.newretail.cart.vo;

import java.math.BigDecimal;
import java.util.List;

public class CartVO {

    private Boolean selectedAll;

    private Integer totalQuantity;

    private BigDecimal cartTotalPrice;

    private List<CartProductVO> cartProductVOList;

    public Boolean getSelectedAll() {
        return selectedAll;
    }

    public void setSelectedAll(Boolean selectedAll) {
        this.selectedAll = selectedAll;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public List<CartProductVO> getCartProductVOList() {
        return cartProductVOList;
    }

    public void setCartProductVOList(List<CartProductVO> cartProductVOList) {
        this.cartProductVOList = cartProductVOList;
    }

    @Override
    public String toString() {
        return "CartVO{" +
                "selectedAll=" + selectedAll +
                ", totalQuantity=" + totalQuantity +
                ", cartTotalPrice=" + cartTotalPrice +
                ", cartProductVOList=" + cartProductVOList +
                '}';
    }

}
