package com.zhihao.newretail.cart.service.impl;

import com.zhihao.newretail.api.cart.vo.CartApiVO;
import com.zhihao.newretail.api.product.feign.ProductFeignService;
import com.zhihao.newretail.api.product.vo.GoodsApiVO;
import com.zhihao.newretail.cart.form.CartAddForm;
import com.zhihao.newretail.cart.form.CartUpdateForm;
import com.zhihao.newretail.cart.pojo.Cart;
import com.zhihao.newretail.cart.pojo.vo.CartProductVO;
import com.zhihao.newretail.cart.pojo.vo.CartVO;
import com.zhihao.newretail.cart.service.CartService;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.redis.util.MyRedisUtil;
import org.apache.http.HttpStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

        /* 获取redis内容 */
        String redisKey = String.format(CART_REDIS_KEY, userId);
        Map<Object, Object> redisMap = redisUtil.getMap(redisKey);

        /* 获取skuId集合 */
        Set<Integer> skuIdSet = new HashSet<>();
        redisMap.forEach((k, v) -> {skuIdSet.add((Integer) k);});
        if (CollectionUtils.isEmpty(skuIdSet)) {
            throw new ServiceException(HttpStatus.SC_NO_CONTENT, "购物车为空");
        }
        /* 获取购物车商品 */
        List<GoodsApiVO> goodsApiVOList = productFeignService.listGoodsApiVOS(skuIdSet);
        if (CollectionUtils.isEmpty(goodsApiVOList)) {
            throw new ServiceException("商品服务繁忙");
        }

        for (Map.Entry<Object, Object> entry : redisMap.entrySet()) {
            Cart cart = (Cart) entry.getValue();
            CartProductVO cartProductVO = new CartProductVO();
            goodsApiVOList.stream()
                    .filter(goodsApiVO -> cart.getSkuId().equals(goodsApiVO.getId()))
                    .forEach(goodsApiVO -> {
                        buildCartProductVO(cart, goodsApiVO, cartProductVO);
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
        GoodsApiVO goodsApiVO = productFeignService.getGoodsApiVO(form.getSkuId());
        try {
            if (ObjectUtils.isEmpty(goodsApiVO.getId())) {
                throw new ServiceException(HttpStatus.SC_NOT_FOUND, "商品下架或删除");
            }
        } catch (NullPointerException e) {
            /*
             * 远程调用返回空对象表示执行熔断保护方法 com.zhihao.newretail.api.product.fallback.ProductFeignFallback.class
             * 远程调用上级服务没有结果 Ribbon 会触发兜底创建对象(对象所有值为null)返回
             * */
            throw new ServiceException("商品服务繁忙");
        }
        String redisKey = String.format(CART_REDIS_KEY, userId);
        Cart cart = (Cart) redisUtil.getMapValue(redisKey, goodsApiVO.getId());
        if (ObjectUtils.isEmpty(cart)) {
            cart = new Cart(goodsApiVO.getSpuId(), goodsApiVO.getId(), form.getQuantity(), form.getSelected());
        } else {
            cart.setQuantity(cart.getQuantity() + form.getQuantity());
        }
        redisUtil.setHash(redisKey, goodsApiVO.getId(), cart);
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
        List<Cart> cartList = listCartS(userId);

        if (!CollectionUtils.isEmpty(cartList)) {
            cartList.forEach(cart -> {
                cart.setSelected(true);
                redisUtil.setHash(redisKey, cart.getSkuId(), cart);
            });
        }
        return getCartVO(userId);
    }

    @Override
    public CartVO updateCartNotSelectedAll(Integer userId) {
        String redisKey = String.format(CART_REDIS_KEY, userId);
        List<Cart> cartList = listCartS(userId);

        if (!CollectionUtils.isEmpty(cartList)) {
            cartList.forEach(cart -> {
                cart.setSelected(false);
                redisUtil.setHash(redisKey, cart.getSkuId(), cart);
            });
        }
        return getCartVO(userId);
    }

    @Override
    public Integer getQuantity(Integer userId) {
        return listCartS(userId).stream().map(Cart::getQuantity).reduce(0, Integer::sum);
    }

    @Override
    public List<CartApiVO> listCartApiVOs(Integer userId) {
        List<Cart> cartList = listCartS(userId);
        return cartList.stream()
                .filter(Cart::getSelected)
                .map(cart -> {
                    CartApiVO cartApiVO = new CartApiVO();
                    BeanUtils.copyProperties(cart, cartApiVO);
                    return cartApiVO;
                }).collect(Collectors.toList());
    }

    @Override
    public void deleteCartBySelected(Integer userId) {
        String redisKey = String.format(CART_REDIS_KEY, userId);
        List<Cart> cartList = listCartS(userId);
        for (Cart cart : cartList) {
            if (cart.getSelected()) {
                redisUtil.deleteEntry(redisKey, cart.getSkuId());
            }
        }
    }

    private List<Cart> listCartS(Integer userId) {
        String redisKey = String.format(CART_REDIS_KEY, userId);
        Map<Object, Object> redisMap = redisUtil.getMap(redisKey);

        List<Cart> cartList = new ArrayList<>();
        redisMap.forEach((k, v) -> {
            cartList.add((Cart) v);
        });
        return cartList;
    }

    private void buildCartProductVO(Cart cart, GoodsApiVO goodsApiVO, CartProductVO cartProductVO) {
        cartProductVO.setSpuId(goodsApiVO.getSpuId());    // 商品ID
        cartProductVO.setSkuId(goodsApiVO.getId());       // 商品规格ID
        cartProductVO.setTitle(goodsApiVO.getTitle());    // 商品标题
        cartProductVO.setSkuImage(goodsApiVO.getSkuImage());  // 商品图片地址
        cartProductVO.setParam(goodsApiVO.getParam());        // 商品规格参数
        cartProductVO.setPrice(goodsApiVO.getPrice());        // 单价
        cartProductVO.setQuantity(cart.getQuantity());      // 数量
        cartProductVO.setTotalPrice(goodsApiVO.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));  // 总价
        cartProductVO.setIsSaleable(goodsApiVO.getIsSaleable());  // 是否有效
        cartProductVO.setSelected(cart.getSelected());          // 是否选中
    }

}
