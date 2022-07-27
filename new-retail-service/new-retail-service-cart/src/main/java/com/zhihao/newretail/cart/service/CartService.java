package com.zhihao.newretail.cart.service;

import com.zhihao.newretail.cart.form.CartAddForm;
import com.zhihao.newretail.cart.form.CartUpdateForm;
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

    /*
    * 购物车更新商品
    * */
    CartVO updateCart(Integer userId, CartUpdateForm form);

    /*
    * 购物车删除商品
    * */
    CartVO deleteCart(Integer userId, Integer skuId);

    /*
    * 购物车商品全选、取消全选
    * */
    CartVO updateCartSelectedAll(Integer userId);

    /*
    * 购物车商品选中数量
    * */
    Integer getQuantity(Integer userId);

}
