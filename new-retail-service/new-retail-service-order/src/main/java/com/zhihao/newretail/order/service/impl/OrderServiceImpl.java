package com.zhihao.newretail.order.service.impl;

import com.zhihao.newretail.api.cart.feign.CartFeignService;
import com.zhihao.newretail.api.cart.vo.CartApiVO;
import com.zhihao.newretail.api.coupons.feign.CouponsFeignService;
import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.api.order.vo.*;
import com.zhihao.newretail.api.product.dto.SkuStockLockApiDTO;
import com.zhihao.newretail.api.product.feign.ProductFeignService;
import com.zhihao.newretail.api.product.feign.StockFeignService;
import com.zhihao.newretail.api.product.vo.GoodsApiVO;
import com.zhihao.newretail.api.product.vo.SkuStockApiVO;
import com.zhihao.newretail.api.user.dto.UserCouponsApiDTO;
import com.zhihao.newretail.api.user.feign.UserAddressFeignService;
import com.zhihao.newretail.api.user.feign.UserCouponsFeignService;
import com.zhihao.newretail.api.user.feign.UserFeignService;
import com.zhihao.newretail.api.user.vo.UserAddressApiVO;
import com.zhihao.newretail.api.user.vo.UserCouponsApiVO;
import com.zhihao.newretail.api.user.vo.UserInfoApiVO;
import com.zhihao.newretail.core.enums.DeleteEnum;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.GsonUtil;
import com.zhihao.newretail.core.util.MyUUIDUtil;
import com.zhihao.newretail.core.util.SnowflakeIdWorker;
import com.zhihao.newretail.order.dao.OrderAddressMapper;
import com.zhihao.newretail.order.dao.OrderItemMapper;
import com.zhihao.newretail.order.dao.OrderMapper;
import com.zhihao.newretail.order.enums.OrderStatusEnum;
import com.zhihao.newretail.order.enums.ProductEnum;
import com.zhihao.newretail.order.form.OrderSubmitForm;
import com.zhihao.newretail.order.pojo.Order;
import com.zhihao.newretail.order.pojo.OrderAddress;
import com.zhihao.newretail.order.pojo.OrderItem;
import com.zhihao.newretail.order.pojo.vo.*;
import com.zhihao.newretail.order.service.OrderMQLogService;
import com.zhihao.newretail.order.service.OrderService;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.rabbitmq.consts.RabbitMQConst;
import com.zhihao.newretail.rabbitmq.dto.coupons.CouponsUnSubMQDTO;
import com.zhihao.newretail.rabbitmq.dto.order.OrderCloseMQDTO;
import com.zhihao.newretail.rabbitmq.dto.stock.StockUnLockMQDTO;
import com.zhihao.newretail.rabbitmq.util.MyRabbitMQUtil;
import com.zhihao.newretail.redis.util.MyRedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartFeignService cartFeignService;
    @Autowired
    private ProductFeignService productFeignService;
    @Autowired
    private StockFeignService stockFeignService;
    @Autowired
    private UserFeignService userFeignService;
    @Autowired
    private UserAddressFeignService userAddressFeignService;
    @Autowired
    private UserCouponsFeignService userCouponsFeignService;
    @Autowired
    private CouponsFeignService couponsFeignService;
    @Autowired
    private OrderMQLogService orderMqLogService;
    @Autowired
    private ThreadPoolExecutor executor;
    @Autowired
    private MyRedisUtil redisUtil;
    @Autowired
    private MyRabbitMQUtil rabbitMQUtil;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderAddressMapper orderAddressMapper;

    private static final String ORDER_TOKEN_REDIS_KEY = "order_token_user_id_%d";

    private static final String REDIS_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    @Override
    public OrderCreateVO getOrderCreateVO(Integer userId) {
        OrderCreateVO orderCreateVO = new OrderCreateVO();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        /* 获取购物车选中的商品 */
        CompletableFuture<List<CartApiVO>> cartListFuture = CompletableFuture.supplyAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            return cartFeignService.listCartApiVOS();
        }, executor);

        CompletableFuture<Void> orderItemCreateListFuture = cartListFuture.thenApplyAsync((res) -> {
                    Set<Integer> skuIdSet = cartApiVOListGetSkuIdSet(res);
                    return productFeignService.listGoodsApiVOS(skuIdSet);       // 获取购物车商品信息
                }, executor)
                .thenApply((res) -> {
                    if (!CollectionUtils.isEmpty(res)) {
                        List<CartApiVO> cartApiVOList = cartListFuture.join();
                        List<OrderItemCreateVO> orderItemCreateVOList = new ArrayList<>();
                        cartApiVOList.forEach(cartApiVO -> {
                            OrderItemCreateVO orderItemCreateVO = new OrderItemCreateVO();
                            buildOrderItemCreateVO(cartApiVO, res, orderItemCreateVO);      // 构造订单项
                            orderItemCreateVOList.add(orderItemCreateVO);
                        });
                        /* 计算商品总价格 */
                        BigDecimal totalPrice = orderItemCreateVOList.stream()
                                .map(OrderItemCreateVO::getTotalPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                        orderCreateVO.setOrderItemCreateVOList(orderItemCreateVOList);
                        orderCreateVO.setTotalPrice(totalPrice);
                        return totalPrice;
                    }
                    return BigDecimal.ZERO;
                })
                .thenAccept((res) -> {
                    RequestContextHolder.setRequestAttributes(requestAttributes);
                    List<UserCouponsApiVO> userCouponsApiVOList = userCouponsFeignService.listUserCouponsApiVOS();
                    if (!CollectionUtils.isEmpty(userCouponsApiVOList)) {
                        Set<Integer> couponsIdSet = userCouponsApiVOList.stream()
                                .map(UserCouponsApiVO::getCouponsId)
                                .collect(Collectors.toSet());
                        List<CouponsApiVO> couponsApiVOList = couponsFeignService.listCouponsApiVOS(couponsIdSet);
                        if (!CollectionUtils.isEmpty(couponsApiVOList)) {
                            /* 筛选可用优惠券(满减) */
                            List<CouponsApiVO> couponsApiVOS = couponsApiVOList.stream()
                                    .filter(couponsApiVO -> res.compareTo(couponsApiVO.getCondition()) > -1)
                                    .collect(Collectors.toList());
                            orderCreateVO.setCouponsApiVOList(couponsApiVOS);
                        }
                    }
                });

        /* 用户收货地址 */
        CompletableFuture<Void> userAddressListFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<UserAddressApiVO> userAddressApiVOList = userAddressFeignService.listUserAddressApiVOS();
            orderCreateVO.setUserAddressApiVOList(userAddressApiVOList);
        }, executor);

        /* 订单唯一标识符，防止重复下单(接口幂等性) */
        CompletableFuture<Void> orderTokenFuture = CompletableFuture.runAsync(() -> {
            String redisKey = String.format(ORDER_TOKEN_REDIS_KEY, userId);
            String orderToken = MyUUIDUtil.getUUID();
            redisUtil.setObject(redisKey, orderToken, 900L);
            orderCreateVO.setOrderToken(orderToken);
        }, executor);

        CompletableFuture.allOf(cartListFuture, orderItemCreateListFuture, userAddressListFuture, orderTokenFuture).join();
        return orderCreateVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long insertOrder(Integer userId, String uuid, OrderSubmitForm form) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        /* 防止重复下单 */
        String redisKey = String.format(ORDER_TOKEN_REDIS_KEY, userId);
        String orderToken = form.getOrderToken();
        Long result = redisUtil.executeScript(REDIS_SCRIPT, redisKey, orderToken);
        if (result == 0) {
            throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "订单唯一标识错误");
        }
        Integer couponsId = form.getCouponsId();                    // 优惠券id
        Long orderNo = getOrderNo(uuid);                            // 订单号
        OrderAddress orderAddress = new OrderAddress();             // 订单收货地址
        OrderCouponsVO orderCouponsVO = new OrderCouponsVO();       // 订单优惠券
        List<OrderItem> orderItemList = new ArrayList<>();          // 订单项列表
        List<SkuStockLockApiDTO> skuStockLockApiDTOList = new ArrayList<>();    // 商品锁定库存信息

        /* 用户收货地址 */
        CompletableFuture<Void> orderAddressFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            try {
                UserAddressApiVO userAddressApiVO = userAddressFeignService.getUserAddressApiVO(form.getAddressId());
                if (ObjectUtils.isEmpty(userAddressApiVO.getId())) {
                    throw new CompletionException("收货地址不存在", new RuntimeException());
                }
                BeanUtils.copyProperties(userAddressApiVO, orderAddress);
                orderAddress.setOrderId(orderNo);
            } catch (NullPointerException e) {
                throw new CompletionException("用户收货地址服务繁忙", new RuntimeException());
            }
        }, executor);

        /* 优惠券 */
        CompletableFuture<Void> orderCouponsFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            if (!ObjectUtils.isEmpty(couponsId)) {
                CouponsApiVO couponsApiVO = couponsFeignService.getCouponsApiVO(couponsId);
                if (ObjectUtils.isEmpty(couponsApiVO)) {
                    throw new CompletionException("优惠券服务繁忙", new RuntimeException());
                }
                BeanUtils.copyProperties(couponsApiVO, orderCouponsVO);
            }
        }, executor);

        /* 购物车选中的商品 */
        CompletableFuture<List<CartApiVO>> cartListFuture = CompletableFuture.supplyAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            return cartFeignService.listCartApiVOS();
        }, executor);

        /* 构造订单项(订单商品) */
        ThreadLocal<List<CartApiVO>> cartListThreadLocal = new ThreadLocal<>();
        ThreadLocal<Set<Integer>> skuIdSetThreadLocal = new ThreadLocal<>();
        CompletableFuture<Void> buildOrderItemFuture = cartListFuture.thenApplyAsync((res) -> {
                    List<CartApiVO> cartApiVOList = cartListFuture.join();
                    cartListThreadLocal.set(cartApiVOList);
                    Set<Integer> skuIdSet = cartApiVOListGetSkuIdSet(cartApiVOList);
                    skuIdSetThreadLocal.set(skuIdSet);
                    return productFeignService.listGoodsApiVOS(skuIdSet);       // 获取商品信息
                }, executor)
                .thenAccept((res) -> {
                    List<CartApiVO> cartApiVOList = cartListThreadLocal.get();
                    Set<Integer> skuIdSet = skuIdSetThreadLocal.get();
                    Map<Integer, GoodsApiVO> goodsApiVOMap;
                    try {
                        goodsApiVOMap = goodsApiVOList2Map(res);
                    } catch (NullPointerException e) {
                        cartListThreadLocal.remove();
                        skuIdSetThreadLocal.remove();
                        throw new CompletionException("商品服务繁忙", new RuntimeException());
                    }
                    /* 获取商品库存信息 */
                    List<SkuStockApiVO> skuStockApiVOList = stockFeignService.listSkuStockApiVOS(skuIdSet);
                    Map<Integer, SkuStockApiVO> skuStockApiVOMap;
                    try {
                        skuStockApiVOMap = skuStockApiVOList2Map(skuStockApiVOList);
                    } catch (NullPointerException e) {
                        cartListThreadLocal.remove();
                        skuIdSetThreadLocal.remove();
                        throw new CompletionException("商品库存服务繁忙", new RuntimeException());
                    }
                    for (CartApiVO cartApiVO : cartApiVOList) {
                        /* 商品有效性校验 */
                        GoodsApiVO goodsApiVO = goodsApiVOMap.get(cartApiVO.getSkuId());
                        if (ProductEnum.NOT_SALEABLE.getCode().equals(goodsApiVO.getIsSaleable())) {
                            cartListThreadLocal.remove();
                            skuIdSetThreadLocal.remove();
                            throw new CompletionException("商品:" + goodsApiVO.getTitle()
                                    + "商品规格ID:" + goodsApiVO.getId()
                                    + "商品下架或删除", new RuntimeException());
                        }
                        /* 库存校验 */
                        SkuStockApiVO skuStockApiVO = skuStockApiVOMap.get(cartApiVO.getSkuId());
                        if (skuStockApiVO.getStock() < cartApiVO.getQuantity()) {
                            cartListThreadLocal.remove();
                            skuIdSetThreadLocal.remove();
                            throw new CompletionException("商品规格ID:" + skuStockApiVO.getSkuId() + "库存不足", new RuntimeException());
                        }
                        /* 构造订单项、锁定库存信息 */
                        OrderItem orderItem = buildOrderItem(orderNo, cartApiVO.getQuantity(), goodsApiVO);
                        orderItemList.add(orderItem);
                        SkuStockLockApiDTO skuStockLockApiDTO = buildSkuStockLock(orderNo, cartApiVO.getQuantity(), goodsApiVO);
                        skuStockLockApiDTOList.add(skuStockLockApiDTO);
                    }
                    cartListThreadLocal.remove();
                    skuIdSetThreadLocal.remove();
                });
        CompletableFuture.allOf(orderAddressFuture, orderCouponsFuture, cartListFuture, buildOrderItemFuture).join();

        /* 订单信息保存数据库 */
        Order order;
        if (ObjectUtils.isEmpty(orderCouponsVO.getId())) {
            order = buildOrder(orderNo, userId, orderItemList);     // 无优惠券
        } else {
            order = buildOrder(orderNo, userId, orderCouponsVO, orderItemList);     // 有优惠券
        }
        order.setMqVersion(RabbitMQConst.CONSUME_VERSION);
        int insertOrderRow = orderMapper.insertSelective(order);
        int insertBatchOrderItemRow = orderItemMapper.insertBatch(orderItemList);
        int insertOrderAddressRow = orderAddressMapper.insertSelective(orderAddress);
        if (insertOrderRow <= 0 || insertBatchOrderItemRow <= 0 || insertOrderAddressRow <=0) {
            throw new ServiceException("订单确认失败");
        }

        /* 锁定商品库存 */
        try {
            int batchStockLockRow = stockFeignService.batchStockLock(skuStockLockApiDTOList);
            if (batchStockLockRow <= 0) {
                throw new ServiceException("库存锁定失败");
            }
        } catch (Exception e) {
            /* 发送库存解锁消息 */
            String stockUnLockMessageContent = buildStockUnLockMessageContent(orderNo);     // 发送内容
            sendDirectMessage(RabbitMQConst.ORDER_STOCK_UNLOCK_ROUTING_KEY, stockUnLockMessageContent);     // 发送消息
            e.printStackTrace();
            throw e;
        }

        /* 优惠券存在，扣除优惠券 */
        if (!ObjectUtils.isEmpty(orderCouponsVO.getId())) {
            UserCouponsApiDTO userCouponsApiDTO = buildUserCouponsApiDTO(orderCouponsVO.getId());
            try {
                int consumeCouponsRow = userCouponsFeignService.consumeCoupons(userCouponsApiDTO);
                if (consumeCouponsRow <= 0) {
                    throw new ServiceException("优惠券扣除异常");
                }
            } catch (Exception e) {
                String stockUnLockMessageContent = buildStockUnLockMessageContent(orderNo);
                sendDirectMessage(RabbitMQConst.ORDER_STOCK_UNLOCK_ROUTING_KEY, stockUnLockMessageContent);
                /* 发送优惠券回退消息 */
                String couponsUnSubMessageContent = buildCouponsUnSubMessageContent(order.getCouponsId());
                sendDirectMessage(RabbitMQConst.ORDER_COUPONS_UNSUB_ROUTING_KEY, couponsUnSubMessageContent);
                e.printStackTrace();
                throw e;
            }
        }
        cartFeignService.deleteCartBySelected();    // 下单成功删除购物车商品
        /* 发送定时消息，未支付订单定时关闭 */
        int delay = 1800000;    // 30分钟
        String orderCloseMessageContent = buildOrderCloseMessageContent(order);     // 发送内容
        sendDelayMessage(orderCloseMessageContent, delay);                          // 发送定时消息
        return orderNo;
    }

    @Override
    public OrderVO getOrderVO(Integer userId, Long orderId) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        OrderVO orderVO = new OrderVO();
        /* 订单信息 */
        CompletableFuture<Void> orderVOFuture = CompletableFuture.supplyAsync(() -> {
                    Order order = orderMapper.selectByPrimaryKey(orderId);
                    if (ObjectUtils.isEmpty(order)
                            || !userId.equals(order.getUserId())
                            || DeleteEnum.DELETE.getCode().equals(order.getIsDelete())) {
                        throw new CompletionException("订单不存在", new RuntimeException());
                    }
                    BeanUtils.copyProperties(order, orderVO);
                    return orderVO;
                }, executor)
                .thenAccept((res) -> {
                    RequestContextHolder.setRequestAttributes(requestAttributes);
                    if (!ObjectUtils.isEmpty(res.getCouponsId())) {
                        CouponsApiVO couponsApiVO = couponsFeignService.getCouponsApiVO(res.getCouponsId());
                        if (!ObjectUtils.isEmpty(couponsApiVO)) {
                            OrderCouponsVO orderCouponsVO = new OrderCouponsVO();
                            BeanUtils.copyProperties(couponsApiVO, orderCouponsVO);
                            orderVO.setOrderCouponsVO(orderCouponsVO);
                        }
                    }
                });
        /* 订单项列表 */
        CompletableFuture<Void> orderItemVOListFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<OrderItem> orderItemList = orderItemMapper.selectListByOrderId(orderId);
            List<GoodsApiVO> goodsApiVOList = listGoodsApiVOS(orderItemList);       // 获取商品信息
            if (!CollectionUtils.isEmpty(goodsApiVOList)) {
                List<OrderItemVO> orderItemVOList = buildOrderItemVOList(orderItemList, goodsApiVOList);
                orderVO.setOrderItemVOList(orderItemVOList);
            }
        }, executor);
        /* 订单收货地址快照 */
        CompletableFuture<Void> orderAddressVOFuture = CompletableFuture.runAsync(() -> {
            OrderAddress orderAddress = orderAddressMapper.selectByPrimaryKey(orderId);
            OrderAddressVO orderAddressVO = new OrderAddressVO();
            BeanUtils.copyProperties(orderAddress, orderAddressVO);
            orderVO.setOrderAddressVO(orderAddressVO);
        }, executor);

        CompletableFuture.allOf(orderVOFuture, orderItemVOListFuture, orderAddressVOFuture).join();
        return orderVO;
    }

    @Override
    public PageUtil<OrderVO> listOrderVOS(Integer userId, Integer status, Integer pageNum, Integer pageSize) {
        PageUtil<OrderVO> pageUtil = new PageUtil<>();
        CompletableFuture<Void> countTotalFuture = CompletableFuture.runAsync(() -> {
            int count = orderMapper.countByUserIdAndStatus(userId, status);
            pageUtil.setPageNum(pageNum);
            pageUtil.setPageSize(pageSize);
            pageUtil.setTotal((long) count);
        }, executor);
        CompletableFuture<Void> listFuture = CompletableFuture.supplyAsync(() ->
                orderMapper.selectList(userId, status, pageNum, pageSize), executor).thenAccept((res) -> {
            if (!CollectionUtils.isEmpty(res)) {
                Set<Long> orderIdSet = res.stream().map(Order::getId).collect(Collectors.toSet());
                List<OrderItem> orderItemList = orderItemMapper.selectListByOrderIdSet(orderIdSet);     // 订单项列表
                List<GoodsApiVO> goodsApiVOList = listGoodsApiVOS(orderItemList);       // 订单商品列表
                if (!CollectionUtils.isEmpty(goodsApiVOList)) {
                    List<OrderItemVO> orderItemVOList = buildOrderItemVOList(orderItemList, goodsApiVOList);
                    List<OrderVO> orderVOList = buildOrderVOList(res, orderItemVOList);
                    pageUtil.setList(orderVOList);
                }
            }
        });
        CompletableFuture.allOf(countTotalFuture, listFuture).join();
        return pageUtil;
    }

    @Override
    public PageUtil<OrderApiVO> listOrderApiVOS(Long orderNo, Integer userId, Integer status, Integer pageNum, Integer pageSize) {
        PageUtil<OrderApiVO> pageUtil = new PageUtil<>();
        ThreadLocal<List<Order>> orderListThreadLocal = new ThreadLocal<>();
        ThreadLocal<Set<Long>> orderNoSetThreadLocal = new ThreadLocal<>();
        ThreadLocal<Set<Integer>> userIdSetThreadLocal = new ThreadLocal<>();
        ThreadLocal<Set<Integer>> couponsIdSetThreadLocal = new ThreadLocal<>();

        CompletableFuture<Void> countTotalFuture = CompletableFuture.runAsync(() -> {
            int count = orderMapper.countByRecord(orderNo, userId, status);
            pageUtil.setPageNum(pageNum);
            pageUtil.setPageSize(pageSize);
            pageUtil.setTotal((long) count);
        }, executor);
        CompletableFuture<Void> listFuture = CompletableFuture.runAsync(() -> {
                    List<Order> orderList = orderMapper.selectOrderOrderAddressList(orderNo, userId, status, pageNum, pageSize);
                    if (!CollectionUtils.isEmpty(orderList)) {
                        orderListThreadLocal.set(orderList);
                        orderNoSetThreadLocal.set(orderList.stream().map(Order::getId).collect(Collectors.toSet()));
                        userIdSetThreadLocal.set(orderList.stream().map(Order::getUserId).collect(Collectors.toSet()));
                        couponsIdSetThreadLocal.set(orderList.stream().map(Order::getCouponsId).collect(Collectors.toSet()));
                    }
                }, executor)
                .thenApply((res) -> {
                    if (!CollectionUtils.isEmpty(orderListThreadLocal.get())) {
                        List<OrderItem> orderItemList = orderItemMapper.selectListByOrderIdSet(orderNoSetThreadLocal.get());
                        List<GoodsApiVO> goodsApiVOList = productFeignService.listGoodsApiVOS(orderItemList.stream().map(OrderItem::getSkuId).collect(Collectors.toSet()));
                        return orderItemList.stream().map(orderItem -> {
                            OrderItemApiVO orderItemApiVO = new OrderItemApiVO();
                            BeanUtils.copyProperties(orderItem, orderItemApiVO);
                            if (!CollectionUtils.isEmpty(goodsApiVOList)) {
                                goodsApiVOList.stream()
                                        .filter(goodsApiVO -> orderItem.getSkuId().equals(goodsApiVO.getId()))
                                        .forEach(goodsApiVO -> {
                                            orderItemApiVO.setSpuId(goodsApiVO.getSpuId());
                                            orderItemApiVO.setTitle(goodsApiVO.getTitle());
                                            orderItemApiVO.setSkuImage(goodsApiVO.getSkuImage());
                                            orderItemApiVO.setParam(goodsApiVO.getParam());
                                        });
                            }
                            return orderItemApiVO;
                        }).collect(Collectors.toList());
                    }
                    return null;
                })
                .thenAccept((res) -> {
                    if (!CollectionUtils.isEmpty(res)) {
                        List<UserInfoApiVO> userInfoApiVOList = userFeignService.listUserInfoApiVOS(userIdSetThreadLocal.get());
                        List<CouponsApiVO> couponsApiVOList = couponsFeignService.listCouponsApiVOS(couponsIdSetThreadLocal.get());
                        List<OrderApiVO> orderApiVOList = orderListThreadLocal.get().stream().map(order -> {
                            OrderApiVO orderApiVO = new OrderApiVO();
                            BeanUtils.copyProperties(order, orderApiVO);
                            userInfoApiVOList.stream().filter(userInfoApiVO -> order.getUserId().equals(userInfoApiVO.getUserId())).map(this::userInfoApiVO2OrderUserApiVO).forEach(orderApiVO::setOrderUserApiVO);
                            if (!CollectionUtils.isEmpty(couponsApiVOList)) {
                                couponsApiVOList.stream().filter(couponsApiVO -> order.getCouponsId().equals(couponsApiVO.getId())).forEach(couponsApiVO -> {
                                    orderApiVO.setDeno(couponsApiVO.getDeno());
                                    orderApiVO.setCondition(couponsApiVO.getCondition());
                                });
                            }
                            OrderAddressApiVO orderAddressApiVO = new OrderAddressApiVO();
                            BeanUtils.copyProperties(order.getOrderAddress(), orderAddressApiVO);
                            orderApiVO.setOrderAddressApiVO(orderAddressApiVO);
                            orderApiVO.setOrderItemApiVOList(res);
                            return orderApiVO;
                        }).collect(Collectors.toList());
                        pageUtil.setList(orderApiVOList);
                    }
                })
                .whenComplete((res, e) -> {
                    orderListThreadLocal.remove();
                    orderNoSetThreadLocal.remove();
                    userIdSetThreadLocal.remove();
                    couponsIdSetThreadLocal.remove();
                });
        CompletableFuture.allOf(countTotalFuture, listFuture).join();
        return pageUtil;
    }

    @Override
    public void updateOrder(Integer userId, Long orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (ObjectUtils.isEmpty(order)
                || !userId.equals(order.getUserId())
                || !OrderStatusEnum.NOT_PAY.getCode().equals(order.getStatus())
                || DeleteEnum.DELETE.getCode().equals(order.getIsDelete())) {
            throw new ServiceException(HttpStatus.SC_NOT_FOUND, "订单不存在");
        }
        /* 取消订单，发送消息直接关闭订单 */
        String orderCloseMessageContent = buildOrderCloseMessageContent(order);
        sendDirectMessage(RabbitMQConst.ORDER_CLOSE_ROUTING_KEY, orderCloseMessageContent);
    }

    @Override
    public void updateOrder(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public int updateOrder(Long orderNo) {
        Order order = orderMapper.selectByPrimaryKey(orderNo);
        if (OrderStatusEnum.PAY_SUCCEED.getCode().equals(order.getStatus())) {
            order.setStatus(OrderStatusEnum.NOT_TAKE.getCode());
            int updateRow = orderMapper.updateByPrimaryKeySelective(order);
            if (updateRow <= 0) {
                throw new ServiceException("发货失败");
            }
            return updateRow;
        }
        throw new ServiceException("当前订单状态不符合发货要求");
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public OrderPayInfoApiVO getOrderPayInfoApiVO(Long orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        OrderPayInfoApiVO orderPayInfoApiVO = new OrderPayInfoApiVO();
        BeanUtils.copyProperties(order, orderPayInfoApiVO);
        return orderPayInfoApiVO;
    }

    /*
    * 构造订单提交页商品列表
    * */
    private void buildOrderItemCreateVO(CartApiVO cartApiVO, List<GoodsApiVO> goodsApiVOList, OrderItemCreateVO orderItemCreateVO) {
        goodsApiVOList.stream()
                .filter(skuApiVO -> cartApiVO.getSkuId().equals(skuApiVO.getId()))
                .forEach(skuApiVO -> {
                    BeanUtils.copyProperties(skuApiVO, orderItemCreateVO);
                    orderItemCreateVO.setSkuId(skuApiVO.getId());
                    orderItemCreateVO.setQuantity(cartApiVO.getQuantity());
                    orderItemCreateVO.setTotalPrice(skuApiVO.getPrice().multiply(BigDecimal.valueOf(cartApiVO.getQuantity())));
                });
    }

    /*
    * 构造订单(无优惠券)
    * */
    private Order buildOrder(Long orderNo, Integer userId, List<OrderItem> orderItemList) {
        BigDecimal amount = totalPrice(orderItemList);
        Order order = new Order();
        order.setId(orderNo);
        order.setUserId(userId);
        order.setAmount(amount);
        order.setActualAmount(amount);
        order.setStatus(OrderStatusEnum.NOT_PAY.getCode());
        return order;
    }

    /*
    * 构造订单(优惠券)
    * */
    private Order buildOrder(Long orderNo, Integer userId, OrderCouponsVO orderCouponsVO, List<OrderItem> orderItemList) {
        BigDecimal amount = totalPrice(orderItemList);
        Order order = new Order();
        order.setId(orderNo);
        order.setUserId(userId);
        order.setAmount(amount);
        order.setActualAmount(amount.subtract(orderCouponsVO.getDeno()));
        order.setCouponsId(orderCouponsVO.getId());
        order.setStatus(OrderStatusEnum.NOT_PAY.getCode());
        return order;
    }

    /*
    * 计算商品总价格
    * */
    private BigDecimal totalPrice(List<OrderItem> orderItemList) {
        return orderItemList.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /*
    * 构造订单项
    * */
    private OrderItem buildOrderItem(Long orderNo, Integer quantity, GoodsApiVO goodsApiVO) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderNo);
        orderItem.setSkuId(goodsApiVO.getId());
        orderItem.setPrice(goodsApiVO.getPrice());
        orderItem.setTotalPrice(goodsApiVO.getPrice().multiply(BigDecimal.valueOf(quantity)));
        orderItem.setNum(quantity);
        return orderItem;
    }

    /*
    * 订单列表
    * */
    private List<OrderVO> buildOrderVOList(List<Order> orderList, List<OrderItemVO> orderItemVOList) {
        Map<Long, List<OrderItemVO>> orderItemVOListMap = orderItemVOList2ListMap(orderItemVOList);
        return orderList.stream().map(order -> {
            List<OrderItemVO> orderItemVOS = orderItemVOListMap.get(order.getId());
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order, orderVO);
            orderVO.setOrderItemVOList(orderItemVOS);
            return orderVO;
        }).collect(Collectors.toList());
    }

    /*
    * 订单项列表
    * */
    private List<OrderItemVO> buildOrderItemVOList(List<OrderItem> orderItemList, List<GoodsApiVO> goodsApiVOList) {
        Map<Integer, GoodsApiVO> goodsApiVOMap = goodsApiVOList2Map(goodsApiVOList);
        return orderItemList.stream().map(orderItem -> {
            OrderItemVO orderItemVO = new OrderItemVO();
            BeanUtils.copyProperties(orderItem, orderItemVO);
            GoodsApiVO goodsApiVO = goodsApiVOMap.get(orderItemVO.getSkuId());
            orderItemVO.setTitle(goodsApiVO.getTitle());
            orderItemVO.setSkuImage(goodsApiVO.getSkuImage());
            orderItemVO.setParam(goodsApiVO.getParam());
            orderItemVO.setPrice(goodsApiVO.getPrice());
            orderItemVO.setTotalPrice(goodsApiVO.getPrice().multiply(BigDecimal.valueOf(orderItemVO.getNum())));
            return orderItemVO;
        }).collect(Collectors.toList());
    }

    private Map<Integer, GoodsApiVO> goodsApiVOList2Map(List<GoodsApiVO> goodsApiVOList) {
        return goodsApiVOList.stream().collect(Collectors.toMap(GoodsApiVO::getId, skuApiVO -> skuApiVO));
    }

    private Map<Integer, SkuStockApiVO> skuStockApiVOList2Map(List<SkuStockApiVO> skuStockApiVOList) {
        return skuStockApiVOList.stream().collect(Collectors.toMap(SkuStockApiVO::getSkuId, skuStockApiVO -> skuStockApiVO));
    }

    private Map<Long, List<OrderItemVO>> orderItemVOList2ListMap(List<OrderItemVO> orderItemVOList) {
        return orderItemVOList.stream().collect(Collectors.groupingBy(OrderItemVO::getOrderId));
    }

    private List<GoodsApiVO> listGoodsApiVOS(List<OrderItem> orderItemList) {
        Set<Integer> skuIdSet = orderItemList.stream().map(OrderItem::getSkuId).collect(Collectors.toSet());
        return productFeignService.listGoodsApiVOS(skuIdSet);
    }

    private Set<Integer> cartApiVOListGetSkuIdSet(List<CartApiVO> cartApiVOList) {
        Set<Integer> skuIdSet;
        try {
            skuIdSet = cartApiVOList.stream().map(CartApiVO::getSkuId).collect(Collectors.toSet());
            if (CollectionUtils.isEmpty(skuIdSet)) {
                throw new CompletionException("请选中商品再下单", new RuntimeException());
            }
        } catch (NullPointerException e) {
            throw new CompletionException("购物车服务繁忙", new RuntimeException());
        }
        return skuIdSet;
    }

    private OrderUserApiVO userInfoApiVO2OrderUserApiVO(UserInfoApiVO userInfoApiVO) {
        OrderUserApiVO orderUserApiVO = new OrderUserApiVO();
        BeanUtils.copyProperties(userInfoApiVO, orderUserApiVO);
        return orderUserApiVO;
    }

    /*
    * 构造锁定库存信息
    * */
    private SkuStockLockApiDTO buildSkuStockLock(Long orderNo, Integer quantity, GoodsApiVO goodsApiVO) {
        SkuStockLockApiDTO skuStockLockApiDTO = new SkuStockLockApiDTO();
        skuStockLockApiDTO.setSpuId(goodsApiVO.getSpuId());
        skuStockLockApiDTO.setSkuId(goodsApiVO.getId());
        skuStockLockApiDTO.setOrderId(orderNo);
        skuStockLockApiDTO.setCount(quantity);
        skuStockLockApiDTO.setMqVersion(RabbitMQConst.CONSUME_VERSION);
        return skuStockLockApiDTO;
    }

    /*
    * 构造优惠券删减信息
    * */
    private UserCouponsApiDTO buildUserCouponsApiDTO(Integer couponsId) {
        UserCouponsApiDTO userCouponsApiDTO = new UserCouponsApiDTO();
        userCouponsApiDTO.setCouponsId(couponsId);
        userCouponsApiDTO.setQuantity(1);
        userCouponsApiDTO.setMqVersion(RabbitMQConst.CONSUME_VERSION);
        return userCouponsApiDTO;
    }

    /*
    * 获取订单号
    * */
    public Long getOrderNo(String uuid) {
        /* 订单号:雪花算法(去重字段) + 用户uuid后3位 */
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 1);
        String str1 = StringUtils.substring(String.valueOf(snowflakeIdWorker.nextId()), 6);
        String str2 = StringUtils.substring(uuid, 12);
        String str3 = str1 + str2;
        return Long.valueOf(str3);
    }

    /*
    * 订单关闭消息内容
    * */
    private String buildOrderCloseMessageContent(Order order) {
        OrderCloseMQDTO orderCloseMQDTO = new OrderCloseMQDTO();
        orderCloseMQDTO.setOrderNo(order.getId());
        orderCloseMQDTO.setCouponsId(order.getCouponsId());
        orderCloseMQDTO.setMqVersion(RabbitMQConst.CONSUME_VERSION);
        return GsonUtil.obj2Json(orderCloseMQDTO);
    }

    /*
    * 库存消息内容
    * */
    private String buildStockUnLockMessageContent(Long orderNo) {
        StockUnLockMQDTO stockUnLockMQDTO = new StockUnLockMQDTO();
        stockUnLockMQDTO.setOrderNo(orderNo);
        stockUnLockMQDTO.setMqVersion(RabbitMQConst.CONSUME_VERSION);
        return GsonUtil.obj2Json(stockUnLockMQDTO);
    }

    /*
    * 优惠券消息内容
    * */
    private String buildCouponsUnSubMessageContent(Integer couponsId) {
        CouponsUnSubMQDTO couponsUnSubMQDTO = new CouponsUnSubMQDTO();
        couponsUnSubMQDTO.setCouponsId(couponsId);
        couponsUnSubMQDTO.setQuantity(1);
        couponsUnSubMQDTO.setMqVersion(RabbitMQConst.CONSUME_VERSION);
        return GsonUtil.obj2Json(couponsUnSubMQDTO);
    }

    /*
    * 发送延迟消息
    * */
    private void sendDelayMessage(String content, Integer delay) {
        Long messageId = orderMqLogService.getMessageId();       // 消息唯一id
        /* 发送消息之前持久化，保证可靠性投递 */
        orderMqLogService.insetMessage(
                messageId,
                content,
                RabbitMQConst.ORDER_NOTIFY_EXCHANGE,
                RabbitMQConst.ORDER_CLOSE_ROUTING_KEY
        );
        rabbitMQUtil.sendMessage(
                RabbitMQConst.ORDER_NOTIFY_EXCHANGE,
                RabbitMQConst.ORDER_CLOSE_ROUTING_KEY,
                content,
                delay,
                new CorrelationData(String.valueOf(messageId))
        );
    }

    /*
    * 发送普通消息
    * */
    private void sendDirectMessage(String routingKey, String content) {
        Long messageId = orderMqLogService.getMessageId();
        orderMqLogService.insetMessage(messageId, content, RabbitMQConst.ORDER_NOTIFY_EXCHANGE, routingKey);
        rabbitMQUtil.sendMessage(
                RabbitMQConst.ORDER_NOTIFY_EXCHANGE,
                routingKey,
                content,
                new CorrelationData(String.valueOf(messageId))
        );
    }

}
