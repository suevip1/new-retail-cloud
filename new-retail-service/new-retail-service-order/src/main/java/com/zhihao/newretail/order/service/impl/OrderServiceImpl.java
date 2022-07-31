package com.zhihao.newretail.order.service.impl;

import com.zhihao.newretail.api.cart.feign.CartFeignService;
import com.zhihao.newretail.api.cart.vo.CartApiVO;
import com.zhihao.newretail.api.coupons.dto.CouponsBatchApiDTO;
import com.zhihao.newretail.api.coupons.feign.CouponsFeignService;
import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.api.product.dto.SkuBatchApiDTO;
import com.zhihao.newretail.api.product.dto.SkuStockBatchApiDTO;
import com.zhihao.newretail.api.product.dto.SkuStockLockApiDTO;
import com.zhihao.newretail.api.product.dto.SkuStockLockBatchApiDTO;
import com.zhihao.newretail.api.product.feign.ProductFeignService;
import com.zhihao.newretail.api.product.feign.ProductStockFeignService;
import com.zhihao.newretail.api.product.vo.SkuApiVO;
import com.zhihao.newretail.api.product.vo.SkuStockApiVO;
import com.zhihao.newretail.api.user.feign.UserAddressFeignService;
import com.zhihao.newretail.api.user.feign.UserCouponsFeignService;
import com.zhihao.newretail.api.user.vo.UserAddressApiVO;
import com.zhihao.newretail.api.user.vo.UserCouponsApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.MyUUIDUtil;
import com.zhihao.newretail.core.util.SnowflakeIdWorker;
import com.zhihao.newretail.order.dao.OrderAddressMapper;
import com.zhihao.newretail.order.dao.OrderItemMapper;
import com.zhihao.newretail.order.dao.OrderMapper;
import com.zhihao.newretail.order.enums.ProductEnum;
import com.zhihao.newretail.order.form.OrderCreateForm;
import com.zhihao.newretail.order.pojo.Order;
import com.zhihao.newretail.order.pojo.OrderAddress;
import com.zhihao.newretail.order.pojo.OrderItem;
import com.zhihao.newretail.order.pojo.vo.*;
import com.zhihao.newretail.order.service.OrderService;
import com.zhihao.newretail.redis.util.MyRedisUtil;
import com.zhihao.newretail.security.UserLoginContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
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
    private ProductStockFeignService productStockFeignService;
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
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderAddressMapper orderAddressMapper;

    private static final String PRESENT = null;

    @Override
    public OrderSubmitVO getOrderSubmitVO() throws ExecutionException, InterruptedException {
        OrderSubmitVO orderSubmitVO = new OrderSubmitVO();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        /* 获取购物车选中商品 */
        CompletableFuture<Void> cartFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);       // 请求内容共享

            /* 获取购物车选中的商品 */
            List<CartApiVO> cartApiVOList = cartFeignService.listCartApiVOs();
            Set<Integer> skuIdSet = cartApiVOList.stream()
                    .map(CartApiVO::getSkuId)
                    .collect(Collectors.toSet());

            if (CollectionUtils.isEmpty(skuIdSet)) {
                throw new ServiceException(HttpStatus.SC_NOT_FOUND, "请选中商品再下单");
            }
            /* 获取商品信息 */
            SkuBatchApiDTO skuBatchApiDTO = new SkuBatchApiDTO();
            skuBatchApiDTO.setIdSet(skuIdSet);
            List<SkuApiVO> skuApiVOList = productFeignService.listSkuApiVOs(skuBatchApiDTO);

            /* 渲染返回 */
            List<ProductSubmitItemVO> productSubmitItemVOList = new ArrayList<>();
            cartApiVOList.forEach(cartApiVO -> {
                ProductSubmitItemVO productSubmitItemVO = new ProductSubmitItemVO();
                buildProductSubmitItemVO(cartApiVO, skuApiVOList, productSubmitItemVO);
                productSubmitItemVOList.add(productSubmitItemVO);
            });

            /* 计算商品总价格 */
            BigDecimal totalPrice = productSubmitItemVOList.stream()
                    .map(ProductSubmitItemVO::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            orderSubmitVO.setProductSubmitItemVOList(productSubmitItemVOList);
            orderSubmitVO.setTotalPrice(totalPrice);
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderVO insertOrder(Integer userId, OrderCreateForm form) throws ExecutionException, InterruptedException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String orderToken = form.getOrderToken();
        Integer couponsId = form.getCouponsId();
        String uuid = UserLoginContext.getUserLoginInfo().getUuid();
        Long orderNo = getOrderNo(uuid);
        OrderAddress orderAddress = new OrderAddress();             // 订单收货地址
        OrderCouponsVO orderCouponsVO = new OrderCouponsVO();       // 是否有优惠券
        List<OrderItem> orderItemList = new ArrayList<>();          // 子订单项
        List<SkuStockLockApiDTO> skuStockLockApiDTOList = new ArrayList<>();    // 商品锁定库存信息

        /* 用户收货地址 */
        CompletableFuture<Void> orderAddressFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            UserAddressApiVO userAddressApiVO = userAddressFeignService.getUserAddressApiVO(form.getAddressId());
            BeanUtils.copyProperties(userAddressApiVO, orderAddress);
            orderAddress.setOrderId(orderNo);
        }, executor);

        /* 优惠券 */
        CompletableFuture<Void> OrderCouponsFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            if (!ObjectUtils.isEmpty(couponsId)) {
                CouponsApiVO couponsApiVO = couponsFeignService.getCouponsApiVO(couponsId);
                BeanUtils.copyProperties(couponsApiVO, orderCouponsVO);
            }
        }, executor);

        /* 获取购物车选中的商品 */
        CompletableFuture<Void> cartApiVOFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<CartApiVO> cartApiVOList = cartFeignService.listCartApiVOs();
            Set<Integer> skuIdSet = cartApiVOList.stream()
                    .map(CartApiVO::getSkuId)
                    .collect(Collectors.toSet());

            SkuBatchApiDTO skuBatchApiDTO = new SkuBatchApiDTO();
            skuBatchApiDTO.setIdSet(skuIdSet);
            List<SkuApiVO> skuApiVOList = productFeignService.listSkuApiVOs(skuBatchApiDTO);

            /* 获取商品库存信息 */
            SkuStockBatchApiDTO skuStockBatchApiDTO = new SkuStockBatchApiDTO();
            skuStockBatchApiDTO.setIdSet(skuIdSet);
            List<SkuStockApiVO> skuStockApiVOList = productStockFeignService.listSkuStockApiVOs(skuStockBatchApiDTO);

            Map<Integer, SkuApiVO> skuApiVOMap = skuApiVOList.stream()
                    .collect(Collectors.toMap(SkuApiVO::getId, skuApiVO -> skuApiVO));
            Map<Integer, SkuStockApiVO> skuStockApiVOMap = skuStockApiVOList.stream()
                    .collect(Collectors.toMap(SkuStockApiVO::getSkuId, skuStockApiVO -> skuStockApiVO));


            for (CartApiVO cartApiVO : cartApiVOList) {
                /* 商品有效性校验 */
                SkuApiVO skuApiVO = skuApiVOMap.get(cartApiVO.getSkuId());
                CompletableFuture<SkuApiVO> skuApiVOFuture = CompletableFuture.supplyAsync(() -> {
                    if (ProductEnum.NOT_SALEABLE.getCode().equals(skuApiVO.getIsSaleable())) {
                        throw new ServiceException(HttpStatus.SC_SERVICE_UNAVAILABLE,
                                "商品:" + skuApiVO.getTitle() + "商品规格ID:" + skuApiVO.getId() + "商品下架或删除");
                    }
                    return skuApiVO;
                }, executor);

                /* 库存校验 */
                SkuStockApiVO skuStockApiVO = skuStockApiVOMap.get(cartApiVO.getSkuId());
                CompletableFuture<Void> skuStockApiVOFuture = CompletableFuture.runAsync(() -> {
                    if (skuStockApiVO.getStock() < cartApiVO.getQuantity()) {
                        throw new ServiceException(HttpStatus.SC_SERVICE_UNAVAILABLE,
                                "商品规格ID:" + skuStockApiVO.getSkuId() + "库存不足");
                    }
                }, executor);

                /* 封装orderItem、库存信息 */
                CompletableFuture<Void> buildListFuture = skuApiVOFuture.thenAcceptAsync((res) -> {
                    OrderItem orderItem = buildOrderItem(orderNo, cartApiVO.getQuantity(), res);
                    SkuStockLockApiDTO skuStockLockApiDTO = buildSkuStockLock(orderNo, cartApiVO.getQuantity(), res);
                    orderItemList.add(orderItem);
                    skuStockLockApiDTOList.add(skuStockLockApiDTO);
                }, executor);

                try {
                    CompletableFuture.allOf(skuApiVOFuture, skuStockApiVOFuture, buildListFuture).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }, executor);

        CompletableFuture.allOf(orderAddressFuture, OrderCouponsFuture, cartApiVOFuture).get();

        Order order = buildOrder(orderNo, userId, orderItemList);
        int insertOrderRow = orderMapper.insertSelective(order);

        int insertBatchOrderItemRow = orderItemMapper.insertBatch(orderItemList);

        int insertOrderAddressRow = orderAddressMapper.insertSelective(orderAddress);

        SkuStockLockBatchApiDTO skuStockLockBatchApiDTO = new SkuStockLockBatchApiDTO();
        skuStockLockBatchApiDTO.setSkuStockLockApiDTOList(skuStockLockApiDTOList);
        productStockFeignService.batchStockLock(skuStockLockBatchApiDTO);
        return null;
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

    /*
    * 构造订单
    * */
    private Order buildOrder(Long orderNo, Integer userId, List<OrderItem> orderItemList) {
        BigDecimal amount = orderItemList.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = new Order();
        order.setId(orderNo);
        order.setUserId(userId);
        order.setAmount(amount);
        order.setActualAmount(amount);
        return order;
    }

    /*
    * 构造子订单
    * */
    private OrderItem buildOrderItem(Long orderNo, Integer quantity, SkuApiVO skuApiVO) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderNo);
        orderItem.setSkuId(skuApiVO.getId());
        orderItem.setPrice(skuApiVO.getPrice());
        orderItem.setTotalPrice(skuApiVO.getPrice().multiply(BigDecimal.valueOf(quantity)));
        orderItem.setNum(quantity);
        return orderItem;
    }

    /*
    * 构造锁定库存信息
    * */
    private SkuStockLockApiDTO buildSkuStockLock(Long orderNo, Integer quantity, SkuApiVO skuApiVO) {
        SkuStockLockApiDTO skuStockLockApiDTO = new SkuStockLockApiDTO();
        skuStockLockApiDTO.setSpuId(skuApiVO.getSpuId());
        skuStockLockApiDTO.setSkuId(skuApiVO.getId());
        skuStockLockApiDTO.setOrderId(orderNo);
        skuStockLockApiDTO.setCount(quantity);
        return skuStockLockApiDTO;
    }

    /*
    * 获取订单号
    * */
    public Long getOrderNo(String uuid) {
        /* 订单号:用户uuid后6位 + 雪花算法(去重字段) */
        String str1 = StringUtils.substring(uuid, 9);
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 1);
        String str2 = StringUtils.substring(String.valueOf(snowflakeIdWorker.nextId()), 6);
        String str3 = str1 + str2;
        return Long.valueOf(str3);
    }

}
