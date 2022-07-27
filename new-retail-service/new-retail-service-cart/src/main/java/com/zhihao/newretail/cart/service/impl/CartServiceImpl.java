package com.zhihao.newretail.cart.service.impl;

import com.zhihao.newretail.api.product.dto.ProductBatchApiDTO;
import com.zhihao.newretail.api.product.dto.SkuBatchApiDTO;
import com.zhihao.newretail.api.product.feign.ProductFeignService;
import com.zhihao.newretail.api.product.vo.ProductApiVO;
import com.zhihao.newretail.api.product.vo.SkuApiVO;
import com.zhihao.newretail.cart.form.CartAddForm;
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
        CartVO cartVO = new CartVO();
        boolean selectedAll = true;
        Integer totalQuantity = 0;
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        List<CartProductVO> cartProductVOList = new ArrayList<>();

        String redisKey = String.format(CART_REDIS_KEY, userId);
        Map<Object, Object> redisMap = redisUtil.getMap(redisKey);

        Set<Integer> skuIdSet = new HashSet<>();
        Set<Integer> spuIdSet = new HashSet<>();
        redisMap.forEach((k, v) -> {
            skuIdSet.add((Integer) k);
            Cart cart = (Cart) v;
            spuIdSet.add(cart.getSpuId());
        });

        if (CollectionUtils.isEmpty(skuIdSet) || CollectionUtils.isEmpty(spuIdSet))
            throw new ServiceException(HttpStatus.SC_NO_CONTENT, "购物车为空");

        SkuBatchApiDTO skuBatchApiDTO = new SkuBatchApiDTO();
        ProductBatchApiDTO productBatchApiDTO = new ProductBatchApiDTO();
        skuBatchApiDTO.setIdSet(skuIdSet);
        productBatchApiDTO.setIdSet(spuIdSet);
        List<SkuApiVO> skuApiVOList = productFeignService.listSkuApiVOs(skuBatchApiDTO);
        List<ProductApiVO> productApiVOList = productFeignService.listProductApiVOs(productBatchApiDTO);

        for (Map.Entry<Object, Object> entry : redisMap.entrySet()) {
            Cart cart = (Cart) entry.getValue();
            CartProductVO cartProductVO = new CartProductVO();
            productApiVOList.stream()
                    .filter(productApiVO -> cart.getSpuId().equals(productApiVO.getId()))
                    .forEach(productApiVO -> {
                        cartProductVO.setSpuId(productApiVO.getId());
                        cartProductVO.setTitle(productApiVO.getTitle());
                    });
            skuApiVOList.stream()
                    .filter(skuApiVO -> cart.getSkuId().equals(skuApiVO.getId()))
                    .forEach(skuApiVO -> {
                        cartProductVO.setSkuId(skuApiVO.getId());
                        cartProductVO.setSkuImage(skuApiVO.getSkuImage());
                        cartProductVO.setParam(skuApiVO.getParam());
                        cartProductVO.setPrice(skuApiVO.getPrice());
                        cartProductVO.setQuantity(cart.getQuantity());
                        cartProductVO.setTotalPrice(skuApiVO.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
                        cartProductVO.setIsSaleable(skuApiVO.getIsSaleable());
                        cartProductVO.setSelected(cart.getSelected());
                    });
            cartProductVOList.add(cartProductVO);

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

}
