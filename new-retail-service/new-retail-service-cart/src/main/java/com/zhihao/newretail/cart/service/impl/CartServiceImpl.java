package com.zhihao.newretail.cart.service.impl;

import com.zhihao.newretail.api.product.dto.SkuBatchApiDTO;
import com.zhihao.newretail.api.product.feign.ProductFeignService;
import com.zhihao.newretail.api.product.vo.SkuApiVO;
import com.zhihao.newretail.cart.form.CartAddForm;
import com.zhihao.newretail.cart.form.CartUpdateForm;
import com.zhihao.newretail.cart.pojo.Cart;
import com.zhihao.newretail.cart.pojo.vo.CartProductVO;
import com.zhihao.newretail.cart.pojo.vo.CartVO;
import com.zhihao.newretail.cart.service.CartService;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.redis.util.MyRedisUtil;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private MyRedisUtil redisUtil;

    @Autowired
    private ProductFeignService productFeignService;

    private static final String CART_REDIS_KEY = "cart_user_id_%d";

    @Override
    public CartVO getCartVO(Integer userId) {
        /* 初始化购物车显示信息 */
        CartVO cartVO = new CartVO();
        boolean selectedAll = true;     // 是否全选
        Integer totalQuantity = 0;      // 购物车商品总数
        BigDecimal cartTotalPrice = BigDecimal.ZERO;    // 总金额
        List<CartProductVO> cartProductVOList = new ArrayList<>();  // 购物车商品列表

        String redisKey = String.format(CART_REDIS_KEY, userId);
        Map<Object, Object> redisMap = redisUtil.getMap(redisKey);
        Set<Integer> skuIdSet = new HashSet<>();    // 获取skuId集合
        redisMap.forEach((k, v) -> {
            skuIdSet.add((Integer) k);
        });

        if (CollectionUtils.isEmpty(skuIdSet))
            throw new ServiceException(HttpStatus.SC_NO_CONTENT, "购物车为空");

        SkuBatchApiDTO skuBatchApiDTO = new SkuBatchApiDTO();
        skuBatchApiDTO.setIdSet(skuIdSet);
        List<SkuApiVO> skuApiVOList = productFeignService.listSkuApiVOs(skuBatchApiDTO);

        for (Map.Entry<Object, Object> entry : redisMap.entrySet()) {
            Cart cart = (Cart) entry.getValue();
            CartProductVO cartProductVO = new CartProductVO();
            skuApiVOList.stream()
                    .filter(skuApiVO -> cart.getSkuId().equals(skuApiVO.getId()))
                    .forEach(skuApiVO -> {
                        cartProductVO.setSpuId(skuApiVO.getSpuId());    // 商品ID
                        cartProductVO.setSkuId(skuApiVO.getId());       // 商品规格ID
                        cartProductVO.setTitle(skuApiVO.getTitle());    // 商品标题
                        cartProductVO.setSkuImage(skuApiVO.getSkuImage());  // 商品图片地址
                        cartProductVO.setParam(skuApiVO.getParam());        // 商品规格参数
                        cartProductVO.setPrice(skuApiVO.getPrice());        // 单价
                        cartProductVO.setQuantity(cart.getQuantity());      // 数量
                        cartProductVO.setTotalPrice(skuApiVO.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));  // 总价
                        cartProductVO.setIsSaleable(skuApiVO.getIsSaleable());  // 是否有效
                        cartProductVO.setSelected(cart.getSelected());          // 是否选中
                        cartProductVOList.add(cartProductVO);
                    });
            if (!cart.getSelected()) {
                selectedAll = false;
            }
            totalQuantity += cart.getQuantity();
            if (cart.getSelected()) {
                cartTotalPrice = cartTotalPrice.add(cartProductVO.getTotalPrice());
            }
        }

        cartVO.setSelectedAll(selectedAll);
        cartVO.setTotalQuantity(totalQuantity);
        cartVO.setCartTotalPrice(cartTotalPrice);
        cartVO.setCartProductVOList(cartProductVOList);
        return cartVO;
    }

    @Override
    public CartVO addCart(Integer userId, CartAddForm form) {
        SkuApiVO skuApiVO = productFeignService.getSkuApiVO(form.getSkuId());

        if (ObjectUtils.isEmpty(skuApiVO) || ObjectUtils.isEmpty(skuApiVO.getId()))
            throw new ServiceException(HttpStatus.SC_NO_CONTENT, "库存不足");

        String redisKey = String.format(CART_REDIS_KEY, userId);
        Cart cart = (Cart) redisUtil.getMapValue(redisKey, skuApiVO.getId());

        if (ObjectUtils.isEmpty(cart)) {
            cart = new Cart(
                    skuApiVO.getSpuId(),
                    skuApiVO.getId(),
                    form.getQuantity(),
                    form.getSelected()
            );
        } else {
            cart.setQuantity(cart.getQuantity() + form.getQuantity());
        }
        redisUtil.setHash(redisKey, skuApiVO.getId(), cart);
        return getCartVO(userId);
    }

    @Override
    public CartVO updateCart(Integer userId, CartUpdateForm form) {
        String redisKey = String.format(CART_REDIS_KEY, userId);
        Cart cart = (Cart) redisUtil.getMapValue(redisKey, form.getSkuId());

        if (ObjectUtils.isEmpty(cart))
            throw new ServiceException(HttpStatus.SC_NOT_FOUND, "购物车无此商品");

        if (form.getQuantity() != null && form.getQuantity() >= 0)
            cart.setQuantity(form.getQuantity());

        if (form.getSelected() != null)
            cart.setSelected(form.getSelected());

        redisUtil.setHash(redisKey, form.getSkuId(), cart);
        return getCartVO(userId);
    }

    @Override
    public CartVO deleteCart(Integer userId, Integer skuId) {
        String redisKey = String.format(CART_REDIS_KEY, userId);
        Cart cart = (Cart) redisUtil.getMapValue(redisKey, skuId);

        if (ObjectUtils.isEmpty(cart))
            throw new ServiceException(HttpStatus.SC_NOT_FOUND, "购物车无此商品");
        redisUtil.deleteEntry(redisKey, skuId);
        return getCartVO(userId);
    }

    @Override
    public CartVO updateCartSelectedAll(Integer userId) {
        String redisKey = String.format(CART_REDIS_KEY, userId);
        List<Cart> cartList = listCarts(userId);

        if (!CollectionUtils.isEmpty(cartList)) {
            cartList.forEach(cart -> {
                cart.setSelected(!cart.getSelected());
                redisUtil.setHash(redisKey, cart.getSkuId(), cart);
            });
        }
        return getCartVO(userId);
    }

    private List<Cart> listCarts(Integer userId) {
        String redisKey = String.format(CART_REDIS_KEY, userId);
        Map<Object, Object> redisMap = redisUtil.getMap(redisKey);

        List<Cart> cartList = new ArrayList<>();
        redisMap.forEach((k, v) -> {
            cartList.add((Cart) v);
        });
        return cartList;
    }

}
