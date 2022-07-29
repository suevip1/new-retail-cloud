package com.zhihao.newretail.order.service.impl;

import com.zhihao.newretail.api.cart.feign.CartFeignService;
import com.zhihao.newretail.api.cart.vo.CartApiVO;
import com.zhihao.newretail.api.coupons.dto.CouponsBatchApiDTO;
import com.zhihao.newretail.api.coupons.feign.CouponsFeignService;
import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.api.product.dto.SkuBatchApiDTO;
import com.zhihao.newretail.api.product.feign.ProductFeignService;
import com.zhihao.newretail.api.product.vo.SkuApiVO;
import com.zhihao.newretail.api.user.feign.UserAddressFeignService;
import com.zhihao.newretail.api.user.feign.UserCouponsFeignService;
import com.zhihao.newretail.api.user.vo.UserAddressApiVO;
import com.zhihao.newretail.api.user.vo.UserCouponsApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.MyUUIDUtil;
import com.zhihao.newretail.order.pojo.vo.OrderSubmitVO;
import com.zhihao.newretail.order.pojo.vo.ProductSubmitItemVO;
import com.zhihao.newretail.order.service.OrderService;
import com.zhihao.newretail.redis.util.MyRedisUtil;
import org.apache.http.HttpStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartFeignService cartFeignService;

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private UserAddressFeignService userAddressFeignService;

    @Autowired
    private UserCouponsFeignService userCouponsFeignService;

    @Autowired
    private CouponsFeignService couponsFeignService;

    @Autowired
    private ThreadPoolExecutor executor;

    @Autowired
    private MyRedisUtil redisUtil;

    private static final String PRESENT = null;

    @Override
    public OrderSubmitVO getOrderSubmitVO() throws ExecutionException, InterruptedException {
        OrderSubmitVO orderSubmitVO = new OrderSubmitVO();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        /* 获取购物车选中商品 */
        CompletableFuture<Void> cartFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);       // 请求内容共享
            final BigDecimal[] totalPrice = {BigDecimal.ZERO};                  // 提交页商品总价

            /* 获取购物车选中的商品 */
            List<CartApiVO> cartApiVOList = cartFeignService.listCartApiVOs();
            Set<Integer> skuIdSet = cartApiVOList.stream()
                    .map(CartApiVO::getSkuId)
                    .collect(Collectors.toSet());

            if (!CollectionUtils.isEmpty(skuIdSet)) {
                /* 获取商品信息 */
                SkuBatchApiDTO skuBatchApiDTO = new SkuBatchApiDTO();
                skuBatchApiDTO.setIdSet(skuIdSet);
                List<SkuApiVO> skuApiVOList = productFeignService.listSkuApiVOs(skuBatchApiDTO);

                /* 渲染返回 */
                List<ProductSubmitItemVO> productSubmitItemVOList = new ArrayList<>();
                cartApiVOList.forEach(cartApiVO -> {
                    ProductSubmitItemVO productSubmitItemVO = new ProductSubmitItemVO();
                    buildProductSubmitItemVO(cartApiVO, skuApiVOList, productSubmitItemVO);
                    totalPrice[0] = totalPrice[0].add(productSubmitItemVO.getTotalPrice());
                    productSubmitItemVOList.add(productSubmitItemVO);
                });
                orderSubmitVO.setProductSubmitItemVOList(productSubmitItemVOList);
                orderSubmitVO.setTotalPrice(totalPrice[0]);
            }
            throw new ServiceException(HttpStatus.SC_NOT_FOUND, "请选中商品再下单");
        }, executor);

        /* 获取用户收货地址列表 */
        CompletableFuture<Void> addressFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<UserAddressApiVO> userAddressApiVOList = userAddressFeignService.listUserAddressApiVOs();
            if (!CollectionUtils.isEmpty(userAddressApiVOList))
                orderSubmitVO.setUserAddressApiVOList(userAddressApiVOList);
        }, executor);

        /* 获取用户优惠券列表 */
        CompletableFuture<Void> couponsFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<UserCouponsApiVO> userCouponsApiVOList = userCouponsFeignService.listUserCouponsApiVOs();
            Set<Integer> couponsIdSet = userCouponsApiVOList.stream()
                    .map(UserCouponsApiVO::getCouponsId)
                    .collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(couponsIdSet)) {
                CouponsBatchApiDTO couponsBatchApiDTO = new CouponsBatchApiDTO();
                couponsBatchApiDTO.setCouponsIdSet(couponsIdSet);
                List<CouponsApiVO> couponsApiVOList = couponsFeignService.listCouponsApiVOs(couponsBatchApiDTO);
                orderSubmitVO.setCouponsApiVOList(couponsApiVOList);
            }
        }, executor);

        /* 返回订单唯一标识符 */
        CompletableFuture<Void> orderTokenFuture = CompletableFuture.runAsync(() -> {
            String orderToken = MyUUIDUtil.getUUID();
            redisUtil.setObject(orderToken, PRESENT, 300L);
            orderSubmitVO.setOrderToken(orderToken);
        }, executor);

        CompletableFuture.allOf(cartFuture, addressFuture, couponsFuture, orderTokenFuture).get();
        return orderSubmitVO;
    }

    /*
    * 构造订单提交页商品列表
    * */
    private void buildProductSubmitItemVO(CartApiVO cartApiVO,
                                          List<SkuApiVO> skuApiVOList,
                                          ProductSubmitItemVO productSubmitItemVO) {
        skuApiVOList.stream()
                .filter(skuApiVO -> cartApiVO.getSkuId().equals(skuApiVO.getId()))
                .forEach(skuApiVO -> {
                    BeanUtils.copyProperties(skuApiVO, productSubmitItemVO);
                    productSubmitItemVO.setSkuId(skuApiVO.getId());
                    productSubmitItemVO.setQuantity(cartApiVO.getQuantity());
                    productSubmitItemVO.setTotalPrice(skuApiVO.getPrice()
                            .multiply(BigDecimal.valueOf(cartApiVO.getQuantity())));
                });
    }

}
