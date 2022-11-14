package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.api.product.vo.GoodsApiVO;
import com.zhihao.newretail.api.product.vo.ProductApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.GsonUtil;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.product.pojo.Sku;
import com.zhihao.newretail.product.pojo.Spu;
import com.zhihao.newretail.product.vo.GoodsVO;
import com.zhihao.newretail.product.vo.ProductInfoVO;
import com.zhihao.newretail.product.vo.ProductVO;
import com.zhihao.newretail.product.vo.ProductDetailVO;
import com.zhihao.newretail.product.service.ProductService;
import com.zhihao.newretail.product.service.SkuService;
import com.zhihao.newretail.product.service.SpuService;
import com.zhihao.newretail.redis.util.MyRedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import static com.zhihao.newretail.product.consts.ProductCacheKeyConst.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private SpuService spuService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private MyRedisUtil redisUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    public ProductDetailVO getProductDetailVO(Integer spuId) {
        RLock lock = redissonClient.getLock(String.format(PRODUCT_DETAIL_LOCK, spuId));     // 获取分布式锁
        lock.lock();
        try {
            String redisKey = String.format(PRODUCT_DETAIL, spuId);     // 获取缓存key
            String str = (String) redisUtil.getObject(redisKey);
            /* 缓存不存在查询数据库 */
            if (StringUtils.isEmpty(str)) {
                ProductDetailVO productDetailVO = new ProductDetailVO();
                CompletableFuture<Void> detailFuture = CompletableFuture.runAsync(() -> {
                    Spu spu = spuService.getSpu(spuId);
                    if (!ObjectUtils.isEmpty(spu)) {
                        ProductInfoVO productInfoVO = new ProductInfoVO();
                        BeanUtils.copyProperties(spu, productDetailVO);
                        BeanUtils.copyProperties(spu.getSpuInfo(), productInfoVO);
                        productDetailVO.setProductInfoVO(productInfoVO);
                    }
                }, executor);
                CompletableFuture<Void> goodsVOListFuture = CompletableFuture.runAsync(() -> {
                    List<Sku> skuList = skuService.listSkuS(spuId);
                    if (!CollectionUtils.isEmpty(skuList)) {
                        List<GoodsVO> goodsVOList = skuList.stream().map(this::sku2GoodsVO).collect(Collectors.toList());
                        productDetailVO.setGoodsVOList(goodsVOList);
                    }
                }, executor);
                CompletableFuture.allOf(detailFuture, goodsVOListFuture).join();
                if (ObjectUtils.isEmpty(productDetailVO.getId())) {
                    redisUtil.setObject(redisKey, PRESENT, 43200L);     // 数据不存在处理缓存穿透
                } else {
                    redisUtil.setObject(redisKey, GsonUtil.obj2Json(productDetailVO));
                }
                return productDetailVO;
            }
            ProductDetailVO productDetailVO = GsonUtil.json2Obj(str, ProductDetailVO.class);
            if (!ObjectUtils.isEmpty(productDetailVO)) {
                return productDetailVO;
            }
            throw new ServiceException("商品不存在");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<GoodsApiVO> listGoodsApiVOS(Set<Integer> skuIdSet) {
        List<Sku> skuList = skuService.listSkuS(skuIdSet);
        Set<Integer> spuIdSet = skuList.stream().map(Sku::getSpuId).collect(Collectors.toSet());
        List<Spu> spuList = spuService.listSpuS(spuIdSet);
        return skuList.stream().map(sku -> {
            GoodsApiVO goodsApiVO = new GoodsApiVO();
            BeanUtils.copyProperties(sku, goodsApiVO);
            spuList.stream().filter(spu -> sku.getSpuId().equals(spu.getId())).forEach(spu -> {
                goodsApiVO.setTitle(spu.getTitle());
            });
            return goodsApiVO;
        }).collect(Collectors.toList());
    }

    @Override
    public GoodsApiVO getGoodsApiVO(Integer skuId) {
        GoodsApiVO goodsApiVO = new GoodsApiVO();
        Sku sku = skuService.getSku(skuId);
        if (!ObjectUtils.isEmpty(sku)) {
            Spu spu = spuService.getSpu(sku.getSpuId());
            BeanUtils.copyProperties(sku, goodsApiVO);
            goodsApiVO.setTitle(spu.getTitle());
        }
        return goodsApiVO;
    }

    @Override
    public PageUtil<ProductVO> listProductVOS(Integer categoryId, Integer pageNum, Integer pageSize) {
        PageUtil<ProductVO> pageUtil = new PageUtil<>();
        CompletableFuture<Void> countTotalFuture = CompletableFuture.runAsync(() -> {
            int total = spuService.countByCategoryId(categoryId);
            pageUtil.setPageNum(pageNum);
            pageUtil.setPageSize(pageSize);
            pageUtil.setTotal((long) total);
        }, executor);
        CompletableFuture<Void> listFuture = CompletableFuture.runAsync(() -> {
            List<Spu> spuList = spuService.listSpuS(categoryId, pageNum, pageSize);
            List<ProductVO> productVOList = spuList.stream().map(spu -> {
                ProductVO productVO = new ProductVO();
                BeanUtils.copyProperties(spu, productVO);
                productVO.setShowImage(spu.getSpuInfo().getShowImage());
                return productVO;
            }).collect(Collectors.toList());
            pageUtil.setList(productVOList);
        }, executor);
        CompletableFuture.allOf(countTotalFuture, listFuture).join();
        return pageUtil;
    }

    @Override
    public PageUtil<ProductApiVO> listProductApiVOS(Integer categoryId, Integer pageNum, Integer pageSize) {
        PageUtil<ProductApiVO> pageUtil = new PageUtil<>();
        CompletableFuture<Void> totalFuture = CompletableFuture.runAsync(() -> {
            int total = spuService.countByCategoryId(categoryId);
            pageUtil.setPageNum(pageNum);
            pageUtil.setPageSize(pageSize);
            pageUtil.setTotal((long) total);
        }, executor);
        CompletableFuture<Void> listFuture = CompletableFuture.runAsync(() -> {
            List<ProductApiVO> productApiVOList = spuService.listSpuS(categoryId, pageNum, pageSize).stream()
                    .map(this::spu2ProductApiVO).collect(Collectors.toList());
            pageUtil.setList(productApiVOList);
        }, executor);
        CompletableFuture.allOf(totalFuture, listFuture).join();
        return pageUtil;
    }

    private GoodsVO sku2GoodsVO(Sku sku) {
        GoodsVO goodsVO = new GoodsVO();
        BeanUtils.copyProperties(sku, goodsVO);
        return goodsVO;
    }

    private GoodsApiVO sku2GoodsApiVO(Sku sku) {
        GoodsApiVO goodsApiVO = new GoodsApiVO();
        BeanUtils.copyProperties(sku, goodsApiVO);
        return goodsApiVO;
    }

    private ProductApiVO spu2ProductApiVO(Spu spu) {
        List<GoodsApiVO> goodsApiVOList = spu.getSkuList().stream().map(this::sku2GoodsApiVO).collect(Collectors.toList());
        ProductApiVO productApiVO = new ProductApiVO();
        BeanUtils.copyProperties(spu, productApiVO);
        productApiVO.setShowImage(spu.getSpuInfo().getShowImage());
        productApiVO.setGoodsApiVOList(goodsApiVOList);
        return productApiVO;
    }

}
