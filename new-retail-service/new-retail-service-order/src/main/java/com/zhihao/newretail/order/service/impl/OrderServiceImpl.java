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
import com.zhihao.newretail.order.enums.OrderStatusEnum;
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

        CompletableFuture<BigDecimal> cartFuture = CompletableFuture.supplyAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);       // 请求内容共享

            /* 获取购物车选中的商品 */
            List<CartApiVO> cartApiVOList = cartFeignService.listCartApiVOs();
            Set<Integer> skuIdSet = cartApiVOList.stream().map(CartApiVO::getSkuId).collect(Collectors.toSet());

            if (CollectionUtils.isEmpty(skuIdSet)) {
                throw new ServiceException(HttpStatus.SC_NOT_FOUND, "请选中商品再下单");
            }
            /* 获取商品信息 */
            SkuBatchApiDTO skuBatchApiDTO = new SkuBatchApiDTO();
            skuBatchApiDTO.setIdSet(skuIdSet);
            List<SkuApiVO> skuApiVOList = productFeignService.listSkuApiVOs(skuBatchApiDTO);

            /* 渲染订单项 */
            List<OrderItemSubmitVO> orderItemSubmitVOList = new ArrayList<>();
            cartApiVOList.forEach(cartApiVO -> {
                OrderItemSubmitVO orderItemSubmitVO = new OrderItemSubmitVO();
                buildOrderItemSubmitVO(cartApiVO, skuApiVOList, orderItemSubmitVO);
                orderItemSubmitVOList.add(orderItemSubmitVO);
            });

            /* 计算商品总价格 */
            BigDecimal totalPrice = orderItemSubmitVOList.stream()
                    .map(OrderItemSubmitVO::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            orderSubmitVO.setOrderItemSubmitVOList(orderItemSubmitVOList);
            orderSubmitVO.setTotalPrice(totalPrice);
            return totalPrice;
        }, executor);

        /* 获取用户收货地址列表 */
        CompletableFuture<Void> addressFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<UserAddressApiVO> userAddressApiVOList = userAddressFeignService.listUserAddressApiVOs();
            orderSubmitVO.setUserAddressApiVOList(userAddressApiVOList);
        }, executor);

        /* 获取用户优惠券列表 */
        CompletableFuture<Void> couponsFuture = cartFuture.thenAcceptAsync((res) -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);

            /* 获取用户优惠券id */
            List<UserCouponsApiVO> userCouponsApiVOList = userCouponsFeignService.listUserCouponsApiVOs();
            Set<Integer> couponsIdSet = userCouponsApiVOList.stream()
                    .map(UserCouponsApiVO::getCouponsId)
                    .collect(Collectors.toSet());

            /* 获取优惠券信息 */
            CouponsBatchApiDTO couponsBatchApiDTO = new CouponsBatchApiDTO();
            couponsBatchApiDTO.setCouponsIdSet(couponsIdSet);
            List<CouponsApiVO> couponsApiVOList = couponsFeignService.listCouponsApiVOs(couponsBatchApiDTO);
            List<CouponsApiVO> couponsApiVOS = couponsApiVOList.stream()
                    .filter(couponsApiVO -> res.compareTo(couponsApiVO.getCondition()) > -1).collect(Collectors.toList());
            orderSubmitVO.setCouponsApiVOList(couponsApiVOS);
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
    public void insertOrder(Integer userId, OrderCreateForm form) throws ExecutionException, InterruptedException {
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
                if (ProductEnum.NOT_SALEABLE.getCode().equals(skuApiVO.getIsSaleable())) {
                    throw new ServiceException(HttpStatus.SC_SERVICE_UNAVAILABLE,
                            "商品:" + skuApiVO.getTitle() + "商品规格ID:" + skuApiVO.getId() + "商品下架或删除");
                }
                /* 库存校验 */
                SkuStockApiVO skuStockApiVO = skuStockApiVOMap.get(cartApiVO.getSkuId());
                if (skuStockApiVO.getStock() < cartApiVO.getQuantity()) {
                    throw new ServiceException(HttpStatus.SC_SERVICE_UNAVAILABLE,
                            "商品规格ID:" + skuStockApiVO.getSkuId() + "库存不足");
                }
                /* 封装orderItem、库存信息 */
                OrderItem orderItem = buildOrderItem(orderNo, cartApiVO.getQuantity(), skuApiVO);
                SkuStockLockApiDTO skuStockLockApiDTO = buildSkuStockLock(orderNo, cartApiVO.getQuantity(), skuApiVO);
                orderItemList.add(orderItem);
                skuStockLockApiDTOList.add(skuStockLockApiDTO);
            }
        }, executor);

        CompletableFuture.allOf(orderAddressFuture, OrderCouponsFuture, cartApiVOFuture).get();

        Order order = buildOrder(orderNo, userId, orderCouponsVO, orderItemList);
        int insertOrderRow = orderMapper.insertSelective(order);
        int insertBatchOrderItemRow = orderItemMapper.insertBatch(orderItemList);
        int insertOrderAddressRow = orderAddressMapper.insertSelective(orderAddress);

        if (insertOrderRow <= 0 || insertBatchOrderItemRow <= 0 || insertOrderAddressRow <=0) {
            throw new ServiceException("订单确认失败");
        }
        SkuStockLockBatchApiDTO skuStockLockBatchApiDTO = new SkuStockLockBatchApiDTO();
        skuStockLockBatchApiDTO.setSkuStockLockApiDTOList(skuStockLockApiDTOList);
        int batchStockLockRow = productStockFeignService.batchStockLock(skuStockLockBatchApiDTO);
        /* 库存锁定失败，分布式事务回滚 */
        if (batchStockLockRow <= 0) {
            throw new ServiceException("库存锁定失败");
        }
    }

    @Override
    public OrderVO getOrderVO(Integer userId, Long orderId) throws ExecutionException, InterruptedException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        OrderVO orderVO = new OrderVO();
        OrderAddressVO orderAddressVO = new OrderAddressVO();
        OrderCouponsVO orderCouponsVO = new OrderCouponsVO();

        CompletableFuture<OrderVO> orderVOFuture = CompletableFuture.supplyAsync(() -> {
            Order order = orderMapper.selectByPrimaryKey(orderId);
            if (ObjectUtils.isEmpty(order) || !userId.equals(order.getUserId())) {
                throw new ServiceException(HttpStatus.SC_NOT_FOUND, "订单不存在");
            }
            BeanUtils.copyProperties(order, orderVO);
            return orderVO;
        }, executor);

        CompletableFuture<Void> orderItemVOListFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<OrderItem> orderItemList = orderItemMapper.selectListByOrderId(orderId);
            Set<Integer> skuIdSet = orderItemList.stream().map(OrderItem::getSkuId).collect(Collectors.toSet());
            SkuBatchApiDTO skuBatchApiDTO = new SkuBatchApiDTO();
            skuBatchApiDTO.setIdSet(skuIdSet);
            List<SkuApiVO> skuApiVOList = productFeignService.listSkuApiVOs(skuBatchApiDTO);
            List<OrderItemVO> orderItemVOList = buildOrderItemVOList(orderItemList, skuApiVOList);
            orderVO.setOrderItemVOList(orderItemVOList);
        }, executor);

        CompletableFuture<Void> orderAddressVOFuture = CompletableFuture.runAsync(() -> {
            OrderAddress orderAddress = orderAddressMapper.selectByPrimaryKey(orderId);
            BeanUtils.copyProperties(orderAddress, orderAddressVO);
            orderVO.setOrderAddressVO(orderAddressVO);
        }, executor);

        CompletableFuture<Void> orderCouponsVOFuture = orderVOFuture.thenAcceptAsync((res) -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            if (!ObjectUtils.isEmpty(res.getCouponsId())) {
                CouponsApiVO couponsApiVO = couponsFeignService.getCouponsApiVO(res.getCouponsId());
                BeanUtils.copyProperties(couponsApiVO, orderCouponsVO);
            }
            orderVO.setOrderCouponsVO(orderCouponsVO);
        }, executor);

        CompletableFuture.allOf(orderVOFuture, orderItemVOListFuture, orderAddressVOFuture, orderCouponsVOFuture).get();
        return orderVO;
    }

    @Override
    public List<OrderVO> listOrderVOs(Integer userId, Integer status) {
        List<Order> orderList = orderMapper.selectListByUserIdAndStatus(userId, status);

        if (CollectionUtils.isEmpty(orderList)) {
            throw new ServiceException(HttpStatus.SC_NO_CONTENT, "暂无数据");
        }
        Set<Long> orderIdSet = orderList.stream().map(Order::getId).collect(Collectors.toSet());
        List<OrderItem> orderItemList = orderItemMapper.selectListByOrderIdSet(orderIdSet);
        List<SkuApiVO> skuApiVOList = buildSkuApiVOList(orderItemList);
        List<OrderItemVO> orderItemVOList = buildOrderItemVOList(orderItemList, skuApiVOList);
        return buildOrderVOList(orderList, orderItemVOList);
    }

    /*
    * 构造订单提交页商品列表
    * */
    private void buildOrderItemSubmitVO(CartApiVO cartApiVO,
                                        List<SkuApiVO> skuApiVOList,
                                        OrderItemSubmitVO orderItemSubmitVO) {
        skuApiVOList.stream()
                .filter(skuApiVO -> cartApiVO.getSkuId().equals(skuApiVO.getId()))
                .forEach(skuApiVO -> {
                    BeanUtils.copyProperties(skuApiVO, orderItemSubmitVO);
                    orderItemSubmitVO.setSkuId(skuApiVO.getId());
                    orderItemSubmitVO.setQuantity(cartApiVO.getQuantity());
                    orderItemSubmitVO.setTotalPrice(skuApiVO.getPrice().multiply(BigDecimal.valueOf(cartApiVO.getQuantity())));
                });
    }

    /*
    * 构造订单
    * */
    private Order buildOrder(Long orderNo, Integer userId, OrderCouponsVO orderCouponsVO, List<OrderItem> orderItemList) {
        BigDecimal amount = orderItemList.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = new Order();
        order.setId(orderNo);
        order.setUserId(userId);
        order.setAmount(amount);
        order.setActualAmount(amount.subtract(orderCouponsVO.getDeno()));
        order.setStatus(OrderStatusEnum.NOT_PAY.getCode());
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

    private List<OrderVO> buildOrderVOList(List<Order> orderList, List<OrderItemVO> orderItemVOList) {
        Map<Long, List<OrderItemVO>> orderItemVOListMap = orderItemVOList.stream()
                .collect(Collectors.groupingBy(OrderItemVO::getOrderId));
        return orderList.stream().map(order -> {
            List<OrderItemVO> orderItemVOS = orderItemVOListMap.get(order.getId());
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order, orderVO);
            orderVO.setOrderItemVOList(orderItemVOS);
            return orderVO;
        }).collect(Collectors.toList());
    }

    private List<OrderItemVO> buildOrderItemVOList(List<OrderItem> orderItemList, List<SkuApiVO> skuApiVOList) {
        Map<Integer, SkuApiVO> skuApiVOMap = skuApiVOList.stream().collect(Collectors.toMap(SkuApiVO::getId, skuApiVO -> skuApiVO));
        return orderItemList.stream().map(orderItem -> {
            OrderItemVO orderItemVO = new OrderItemVO();
            BeanUtils.copyProperties(orderItem, orderItemVO);
            SkuApiVO skuApiVO = skuApiVOMap.get(orderItemVO.getSkuId());
            orderItemVO.setTitle(skuApiVO.getTitle());
            orderItemVO.setSkuImage(skuApiVO.getSkuImage());
            orderItemVO.setParam(skuApiVO.getParam());
            orderItemVO.setPrice(skuApiVO.getPrice());
            orderItemVO.setTotalPrice(skuApiVO.getPrice().multiply(BigDecimal.valueOf(orderItemVO.getNum())));
            return orderItemVO;
        }).collect(Collectors.toList());
    }

    private List<SkuApiVO> buildSkuApiVOList(List<OrderItem> orderItemList) {
        Set<Integer> skuIdSet = orderItemList.stream().map(OrderItem::getSkuId).collect(Collectors.toSet());
        SkuBatchApiDTO skuBatchApiDTO = new SkuBatchApiDTO();
        skuBatchApiDTO.setIdSet(skuIdSet);
        return productFeignService.listSkuApiVOs(skuBatchApiDTO);
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
        /* 订单号:用户uuid后3位 + 雪花算法(去重字段) */
        String str1 = StringUtils.substring(uuid, 12);
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 1);
        String str2 = StringUtils.substring(String.valueOf(snowflakeIdWorker.nextId()), 6);
        String str3 = str1 + str2;
        return Long.valueOf(str3);
    }

}
