package com.zhihao.newretail.order.service.impl;

import com.zhihao.newretail.api.cart.feign.CartFeignService;
import com.zhihao.newretail.api.cart.vo.CartApiVO;
import com.zhihao.newretail.api.coupons.feign.CouponsFeignService;
import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.api.order.dto.OrderLogisticsInfoAddApiDTO;
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
import com.zhihao.newretail.order.dao.OrderLogisticsInfoMapper;
import com.zhihao.newretail.order.dao.OrderMapper;
import com.zhihao.newretail.order.enums.OrderStatusEnum;
import com.zhihao.newretail.order.enums.ProductEnum;
import com.zhihao.newretail.order.form.OrderSubmitForm;
import com.zhihao.newretail.order.pojo.Order;
import com.zhihao.newretail.order.pojo.OrderAddress;
import com.zhihao.newretail.order.pojo.OrderItem;
import com.zhihao.newretail.order.pojo.OrderLogisticsInfo;
import com.zhihao.newretail.order.pojo.vo.*;
import com.zhihao.newretail.order.service.OrderMQLogService;
import com.zhihao.newretail.order.service.OrderService;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.rabbitmq.dto.coupons.CouponsUnSubMQDTO;
import com.zhihao.newretail.rabbitmq.dto.order.OrderCloseMQDTO;
import com.zhihao.newretail.rabbitmq.dto.stock.StockUnLockMQDTO;
import com.zhihao.newretail.redis.util.MyRedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

import static com.zhihao.newretail.rabbitmq.consts.RabbitMQConst.*;

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
    private UserAddressFeignService userAddressFeignService;
    @Autowired
    private UserCouponsFeignService userCouponsFeignService;
    @Autowired
    private UserFeignService userFeignService;
    @Autowired
    private CouponsFeignService couponsFeignService;
    @Autowired
    private OrderMQLogService orderMqLogService;
    @Autowired
    private ThreadPoolExecutor executor;
    @Autowired
    private MyRedisUtil redisUtil;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderAddressMapper orderAddressMapper;
    @Autowired
    private OrderLogisticsInfoMapper orderLogisticsInfoMapper;

    private static final String ORDER_TOKEN_REDIS_KEY = "order_token_user_id_%d";

    @Override
    public OrderCreateVO getOrderCreateVO(Integer userId) {
        OrderCreateVO orderCreateVO = new OrderCreateVO();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ThreadLocal<List<CartApiVO>> cartListThreadLocal = new ThreadLocal<>();
        CompletableFuture<Void> orderItemCreateFuture = CompletableFuture.supplyAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<CartApiVO> cartApiVOList = cartFeignService.listCartApiVOS();
            cartListThreadLocal.set(cartApiVOList);
            return cartApiVOList;
        }, executor).thenApply((res) -> {
            Set<Integer> skuIdSet = cartApiVOListGetSkuIdSet(res);
            return productFeignService.listGoodsApiVOS(skuIdSet);       // 获取购物车商品信息
        }).thenApply((res) -> {
            if (!CollectionUtils.isEmpty(res)) {
                List<CartApiVO> cartApiVOList = cartListThreadLocal.get();
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
        }).thenAccept((res) -> {
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
        }).whenComplete((res, e) -> {
            cartListThreadLocal.remove();
            log.info("'getOrderCreateVO()', ThreadLocal清理完毕.");
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

        CompletableFuture.allOf(orderItemCreateFuture, userAddressListFuture, orderTokenFuture).join();
        return orderCreateVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long insertOrder(Integer userId, String uuid, OrderSubmitForm form) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        /* 防止重复下单 */
        String redisKey = String.format(ORDER_TOKEN_REDIS_KEY, userId);
        String orderToken = form.getOrderToken();
        Long result = redisUtil.executeScript(MyRedisUtil.REDIS_SCRIPT, redisKey, orderToken);
        if (result == 0) {
            throw new ServiceException("订单唯一标识错误，请刷新页面");
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

        /* 构造订单 */
        ThreadLocal<List<CartApiVO>> cartListThreadLocal = new ThreadLocal<>();
        ThreadLocal<Set<Integer>> skuIdSetThreadLocal = new ThreadLocal<>();
        CompletableFuture<Void> buildOrderItemFuture = CompletableFuture.supplyAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            return cartFeignService.listCartApiVOS();
        }, executor).thenApply((res) -> {
            cartListThreadLocal.set(res);
            Set<Integer> skuIdSet = cartApiVOListGetSkuIdSet(res);
            skuIdSetThreadLocal.set(skuIdSet);
            return productFeignService.listGoodsApiVOS(skuIdSet);       // 获取商品信息
        }).thenAccept((res) -> {
            List<CartApiVO> cartApiVOList = cartListThreadLocal.get();
            Set<Integer> skuIdSet = skuIdSetThreadLocal.get();
            Map<Integer, GoodsApiVO> goodsApiVOMap;
            try {
                goodsApiVOMap = goodsApiVOList2Map(res);
            } catch (NullPointerException e) {
                throw new CompletionException("商品服务繁忙", new RuntimeException());
            }
            /* 获取商品库存信息 */
            List<SkuStockApiVO> skuStockApiVOList = stockFeignService.listSkuStockApiVOS(skuIdSet);
            Map<Integer, SkuStockApiVO> skuStockApiVOMap;
            try {
                skuStockApiVOMap = skuStockApiVOList2Map(skuStockApiVOList);
            } catch (NullPointerException e) {
                throw new CompletionException("商品库存服务繁忙", new RuntimeException());
            }
            for (CartApiVO cartApiVO : cartApiVOList) {
                /* 商品有效性校验 */
                GoodsApiVO goodsApiVO = goodsApiVOMap.get(cartApiVO.getSkuId());
                if (ProductEnum.NOT_SALEABLE.getCode().equals(goodsApiVO.getIsSaleable())) {
                    throw new CompletionException("商品:" + goodsApiVO.getTitle() +
                            "商品规格ID:" + goodsApiVO.getId() +
                            "商品下架或删除", new RuntimeException());
                }
                /* 库存校验 */
                SkuStockApiVO skuStockApiVO = skuStockApiVOMap.get(cartApiVO.getSkuId());
                if (skuStockApiVO.getStock() < cartApiVO.getQuantity()) {
                    throw new CompletionException("商品规格ID:" + skuStockApiVO.getSkuId() + "库存不足", new RuntimeException());
                }
                /* 构造订单项、锁定库存信息 */
                OrderItem orderItem = buildOrderItem(orderNo, cartApiVO.getQuantity(), goodsApiVO);
                orderItemList.add(orderItem);
                SkuStockLockApiDTO skuStockLockApiDTO = buildSkuStockLock(orderNo, cartApiVO.getQuantity(), goodsApiVO);
                skuStockLockApiDTOList.add(skuStockLockApiDTO);
            }

        }).whenComplete((res, e) -> {
            cartListThreadLocal.remove();
            skuIdSetThreadLocal.remove();
            log.info("'insertOrder()', ThreadLocal清理完毕.");
        });
        CompletableFuture.allOf(orderAddressFuture, orderCouponsFuture, buildOrderItemFuture).join();
        /* 订单信息保存数据库 */
        Order order;
        if (ObjectUtils.isEmpty(orderCouponsVO.getId())) {
            order = buildOrder(orderNo, userId, orderItemList);     // 无优惠券
        } else {
            order = buildOrder(orderNo, userId, orderCouponsVO, orderItemList);     // 有优惠券
        }
        order.setMqVersion(CONSUME_VERSION);
        try {
            int insertOrderRow = orderMapper.insertSelective(order);
            if (insertOrderRow <= 0) {
                throw new ServiceException("订单确认失败");
            }
            int insertBatchOrderItemRow = orderItemMapper.insertBatch(orderItemList);
            if (insertBatchOrderItemRow <= 0) {
                throw new ServiceException("订单项列表确认失败");
            }
            int insertOrderAddressRow = orderAddressMapper.insertSelective(orderAddress);
            if (insertOrderAddressRow <= 0) {
                throw new ServiceException("订单收货地址确认失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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
            sendMessage(ORDER_STOCK_UNLOCK_ROUTING_KEY, stockUnLockMessageContent);
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
                sendMessage(ORDER_STOCK_UNLOCK_ROUTING_KEY, stockUnLockMessageContent);
                /* 发送优惠券回退消息 */
                String couponsUnSubMessageContent = buildCouponsUnSubMessageContent(order.getCouponsId());
                sendMessage(ORDER_COUPONS_UNSUB_ROUTING_KEY, couponsUnSubMessageContent);
                e.printStackTrace();
                throw e;
            }
        }
        /* 发送定时消息，未支付订单定时关闭 */
        int delay = 900000;     // 15分钟
        // int delay = 60000;
        String orderCloseMessageContent = buildOrderCloseMessageContent(order);     // 发送内容
        sendMessage(orderCloseMessageContent, delay);
        cartFeignService.deleteCartBySelected();    // 下单成功删除购物车商品
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
        }, executor).thenAccept((res) -> {
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
            if (!ObjectUtils.isEmpty(orderAddress)) {
                BeanUtils.copyProperties(orderAddress, orderAddressVO);
            }
            orderVO.setOrderAddressVO(orderAddressVO);
        }, executor);
        /* 订单快递信息 */
        CompletableFuture<Void> orderLogisticsInfoVOFuture = CompletableFuture.runAsync(() -> {
            OrderLogisticsInfo orderLogisticsInfo = orderLogisticsInfoMapper.selectByOrderId(orderId);
            OrderLogisticsInfoVO orderLogisticsInfoVO = new OrderLogisticsInfoVO();
            if (!ObjectUtils.isEmpty(orderLogisticsInfo)) {
                orderLogisticsInfoVO.setLogisticsId(orderLogisticsInfo.getLogisticsId());
                orderLogisticsInfoVO.setLogisticsCompany(orderLogisticsInfo.getLogisticsCompany());
            }
            orderVO.setOrderLogisticsInfoVO(orderLogisticsInfoVO);
        }, executor);
        CompletableFuture.allOf(orderVOFuture, orderItemVOListFuture, orderAddressVOFuture, orderLogisticsInfoVOFuture).join();
        return orderVO;
    }

    @Override
    public OrderApiVO getOrderApiVO(Long orderNo) {
        OrderApiVO orderApiVO = new OrderApiVO();
        CompletableFuture<OrderApiVO> orderApiVOFuture = CompletableFuture.supplyAsync(() -> {
            Order order = orderMapper.selectByPrimaryKey(orderNo);
            BeanUtils.copyProperties(order, orderApiVO);
            return orderApiVO;
        }, executor);

        CompletableFuture<Void> orderUserApiVOFuture = orderApiVOFuture.thenAcceptAsync((res) -> {
            UserInfoApiVO userInfoApiVO = userFeignService.getUserInfoApiVO(res.getUserId());
            if (!ObjectUtils.isEmpty(userInfoApiVO)) {
                res.setOrderUserApiVO(userInfoApiVO2OrderUserApiVO(userInfoApiVO));
            }
        }, executor);

        CompletableFuture<Void> orderAddressApiVOFuture = orderApiVOFuture.thenAcceptAsync((res) -> {
            OrderAddress orderAddress = orderAddressMapper.selectByPrimaryKey(orderNo);
            OrderAddressApiVO orderAddressApiVO = new OrderAddressApiVO();
            if (!ObjectUtils.isEmpty(orderAddress)) {
                BeanUtils.copyProperties(orderAddress, orderAddressApiVO);
            }
            res.setOrderAddressApiVO(orderAddressApiVO);
        }, executor);

        CompletableFuture<Void> orderItemApiVOListFuture = orderApiVOFuture.thenAcceptAsync((res) -> {
            List<OrderItem> orderItemList = orderItemMapper.selectListByOrderId(orderNo);
            List<GoodsApiVO> goodsApiVOList = listGoodsApiVOS(orderItemList);
            if (!CollectionUtils.isEmpty(goodsApiVOList)) {
                List<OrderItemApiVO> orderItemApiVOList = orderItemList2OrderItemApiVOList(orderItemList, goodsApiVOList);
                res.setOrderItemApiVOList(orderItemApiVOList);
            }
        }, executor);

        CompletableFuture<Void> orderLogisticsInfoApiVOFuture = orderApiVOFuture.thenAcceptAsync((res) -> {
            OrderLogisticsInfo orderLogisticsInfo = orderLogisticsInfoMapper.selectByOrderId(orderNo);
            OrderLogisticsInfoApiVO orderLogisticsInfoApiVO = new OrderLogisticsInfoApiVO();
            if (!ObjectUtils.isEmpty(orderLogisticsInfo)) {
                orderLogisticsInfoApiVO.setLogisticsId(orderLogisticsInfo.getLogisticsId());
                orderLogisticsInfoApiVO.setLogisticsCompany(orderLogisticsInfo.getLogisticsCompany());
            }
            res.setOrderLogisticsInfoApiVO(orderLogisticsInfoApiVO);
        }, executor);
        CompletableFuture.allOf(orderApiVOFuture, orderUserApiVOFuture, orderAddressApiVOFuture, orderItemApiVOListFuture, orderLogisticsInfoApiVOFuture).join();
        return orderApiVO;
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
        CompletableFuture<Void> listFuture = CompletableFuture.runAsync(() -> {
            List<Order> orderList = orderMapper.selectList(userId, status, pageNum, pageSize);
            if (!CollectionUtils.isEmpty(orderList)) {
                Set<Long> orderIdSet = orderList.stream().map(Order::getId).collect(Collectors.toSet());
                List<OrderItem> orderItemList = orderItemMapper.selectListByOrderIdSet(orderIdSet);     // 订单项列表
                List<GoodsApiVO> goodsApiVOList = listGoodsApiVOS(orderItemList);       // 订单商品列表
                if (!CollectionUtils.isEmpty(goodsApiVOList)) {
                    List<OrderItemVO> orderItemVOList = buildOrderItemVOList(orderItemList, goodsApiVOList);
                    List<OrderVO> orderVOList = buildOrderVOList(orderList, orderItemVOList);
                    pageUtil.setList(orderVOList);
                }
            }
        }, executor);
        CompletableFuture.allOf(countTotalFuture, listFuture).join();
        return pageUtil;
    }

    @Override
    public PageUtil<OrderApiVO> listOrderApiVOS(Long orderNo, Integer userId, Integer status, Integer pageNum, Integer pageSize) {
        PageUtil<OrderApiVO> pageUtil = new PageUtil<>();

        CompletableFuture<Void> countTotalFuture = CompletableFuture.runAsync(() -> {
            int count = orderMapper.countByRecord(orderNo, userId, status);
            pageUtil.setPageNum(pageNum);
            pageUtil.setPageSize(pageSize);
            pageUtil.setTotal((long) count);
        }, executor);

        CompletableFuture<Void> listFuture = CompletableFuture.runAsync(() -> {
            List<Order> orderList = orderMapper.selectOrderOrderAddressList(orderNo, userId, status, pageNum, pageSize);
            Set<Long> orderIdSet = orderList.stream().map(Order::getId).collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(orderIdSet)) {
                List<OrderLogisticsInfo> orderLogisticsInfoList = orderLogisticsInfoMapper.selectListByOrderIdSet(orderIdSet);
                List<OrderApiVO> orderApiVOList = orderList.stream().map(order -> {
                    OrderAddressApiVO orderAddressApiVO = new OrderAddressApiVO();
                    BeanUtils.copyProperties(order.getOrderAddress(), orderAddressApiVO);
                    OrderApiVO orderApiVO = new OrderApiVO();
                    BeanUtils.copyProperties(order, orderApiVO);
                    orderApiVO.setOrderAddressApiVO(orderAddressApiVO);
                    if (!CollectionUtils.isEmpty(orderLogisticsInfoList)) {
                        orderLogisticsInfoList.stream()
                                .filter(orderLogisticsInfo -> order.getId().equals(orderLogisticsInfo.getOrderId()))
                                .forEach(orderLogisticsInfo -> {
                                    OrderLogisticsInfoApiVO orderLogisticsInfoApiVO = new OrderLogisticsInfoApiVO();
                                    BeanUtils.copyProperties(orderLogisticsInfo, orderLogisticsInfoApiVO);
                                    orderApiVO.setOrderLogisticsInfoApiVO(orderLogisticsInfoApiVO);
                                });
                    }
                    return orderApiVO;
                }).collect(Collectors.toList());
                pageUtil.setList(orderApiVOList);
            }
        }, executor);
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
            throw new ServiceException("订单不存在");
        }
        /* 取消订单，发送消息直接关闭订单 */
        String orderCloseMessageContent = buildOrderCloseMessageContent(order);
        sendMessage(ORDER_CLOSE_ROUTING_KEY, orderCloseMessageContent);
    }

    @Override
    public void updateOrder(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public void takeAnOrder(Integer userId, Long orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (ObjectUtils.isEmpty(order) || !userId.equals(order.getUserId())) {
            throw new ServiceException("订单不存在");
        } else if (OrderStatusEnum.NOT_TAKE.getCode().equals(order.getStatus())) {
            order.setStatus(OrderStatusEnum.TAKE_SUCCEED.getCode());
            orderMapper.updateByPrimaryKeySelective(order);
        } else {
            throw new ServiceException("订单未满足收货条件");
        }
    }

    @Override
    @Transactional
    public int updateOrder(Long orderNo, OrderLogisticsInfoAddApiDTO orderLogisticsInfoAddApiDTO) {
        Order order = orderMapper.selectByPrimaryKey(orderNo);
        if (OrderStatusEnum.PAY_SUCCEED.getCode().equals(order.getStatus())) {
            order.setStatus(OrderStatusEnum.NOT_TAKE.getCode());
            OrderLogisticsInfo orderLogisticsInfo = new OrderLogisticsInfo();
            orderLogisticsInfo.setOrderId(orderNo);
            orderLogisticsInfo.setLogisticsId(orderLogisticsInfoAddApiDTO.getLogisticsId());
            orderLogisticsInfo.setLogisticsCompany(orderLogisticsInfoAddApiDTO.getLogisticsCompany());
            int updateRow = orderMapper.updateByPrimaryKeySelective(order);
            int insertRow = orderLogisticsInfoMapper.insertSelective(orderLogisticsInfo);
            if (updateRow <= 0 || insertRow <= 0) {
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

    private List<OrderItemApiVO> orderItemList2OrderItemApiVOList(List<OrderItem> orderItemList, List<GoodsApiVO> goodsApiVOList) {
        return orderItemList.stream().map(orderItem -> {
            OrderItemApiVO orderItemApiVO = new OrderItemApiVO();
            BeanUtils.copyProperties(orderItem, orderItemApiVO);
            if (!CollectionUtils.isEmpty(goodsApiVOList)) {
                goodsApiVOList.stream().filter(goodsApiVO -> orderItem.getSkuId().equals(goodsApiVO.getId())).forEach(goodsApiVO -> {
                    orderItemApiVO.setSpuId(goodsApiVO.getSpuId());
                    orderItemApiVO.setTitle(goodsApiVO.getTitle());
                    orderItemApiVO.setSkuImage(goodsApiVO.getSkuImage());
                    orderItemApiVO.setParam(goodsApiVO.getParam());
                });
            }
            return orderItemApiVO;
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
        skuStockLockApiDTO.setMqVersion(CONSUME_VERSION);
        return skuStockLockApiDTO;
    }

    /*
    * 构造优惠券删减信息
    * */
    private UserCouponsApiDTO buildUserCouponsApiDTO(Integer couponsId) {
        UserCouponsApiDTO userCouponsApiDTO = new UserCouponsApiDTO();
        userCouponsApiDTO.setCouponsId(couponsId);
        userCouponsApiDTO.setQuantity(1);
        userCouponsApiDTO.setMqVersion(CONSUME_VERSION);
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
        orderCloseMQDTO.setMqVersion(CONSUME_VERSION);
        return GsonUtil.obj2Json(orderCloseMQDTO);
    }

    /*
    * 库存消息内容
    * */
    private String buildStockUnLockMessageContent(Long orderNo) {
        StockUnLockMQDTO stockUnLockMQDTO = new StockUnLockMQDTO();
        stockUnLockMQDTO.setOrderNo(orderNo);
        stockUnLockMQDTO.setMqVersion(CONSUME_VERSION);
        return GsonUtil.obj2Json(stockUnLockMQDTO);
    }

    /*
    * 优惠券消息内容
    * */
    private String buildCouponsUnSubMessageContent(Integer couponsId) {
        CouponsUnSubMQDTO couponsUnSubMQDTO = new CouponsUnSubMQDTO();
        couponsUnSubMQDTO.setCouponsId(couponsId);
        couponsUnSubMQDTO.setQuantity(1);
        couponsUnSubMQDTO.setMqVersion(CONSUME_VERSION);
        return GsonUtil.obj2Json(couponsUnSubMQDTO);
    }

    /*
    * 发送消息
    * */
    private void sendMessage(String routingKey, String content) {
        Long messageId = orderMqLogService.getMessageId();       // 消息唯一id
        /* 发送消息之前持久化，保证可靠性投递 */
        int insetMessageRow = orderMqLogService.insetMessage(messageId, content, ORDER_NOTIFY_EXCHANGE, routingKey);
        if (insetMessageRow > 0) {
            rabbitTemplate.convertAndSend(ORDER_NOTIFY_EXCHANGE, routingKey, content, new CorrelationData(String.valueOf(messageId)));
            log.info("订单服务，发送普通消息");
        }
    }

    private void sendMessage(String content, Integer delay) {
        Long messageId = orderMqLogService.getMessageId();       // 消息唯一id
        int insetMessageRow = orderMqLogService.insetMessage(messageId, content, ORDER_NOTIFY_EXCHANGE, ORDER_CLOSE_ROUTING_KEY);
        if (insetMessageRow > 0) {
            rabbitTemplate.convertAndSend(ORDER_NOTIFY_EXCHANGE, ORDER_CLOSE_ROUTING_KEY, content, message -> {
                message.getMessageProperties().setDelay(delay);
                return message;
            }, new CorrelationData(String.valueOf(messageId)));
            log.info("订单服务，发送延迟消息");
        }
    }

}
