package com.zhihao.newretail.cart.service;

import com.zhihao.newretail.cart.form.CartAddForm;
import com.zhihao.newretail.cart.pojo.vo.CartVO;

public interface CartService {

    /*
    * 购物车商品
    * */
    CartVO getCartVO(Integer userId);

    /*
    * 购物车添加商品
    * */
    CartVO addCart(Integer userId, CartAddForm form);

}