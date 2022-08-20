package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.api.product.vo.GoodsApiVO;
import com.zhihao.newretail.api.product.vo.ProductApiVO;
import com.zhihao.newretail.product.pojo.Sku;
import com.zhihao.newretail.product.pojo.Spu;
import com.zhihao.newretail.product.pojo.SpuInfo;
import com.zhihao.newretail.product.pojo.vo.GoodsVO;
import com.zhihao.newretail.product.pojo.vo.ProductInfoVO;
import com.zhihao.newretail.product.pojo.vo.ProductVO;
import com.zhihao.newretail.product.pojo.vo.ProductDetailVO;
import com.zhihao.newretail.product.service.ProductService;
import com.zhihao.newretail.product.service.SkuService;
import com.zhihao.newretail.product.service.SpuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private SpuService spuService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    public ProductDetailVO getProductDetailVO(Integer spuId) throws ExecutionException, InterruptedException {
        ProductDetailVO productDetailVO = new ProductDetailVO();

        CompletableFuture<Void> detailFuture = CompletableFuture.runAsync(() -> {
            Spu spu = spuService.getSpu(spuId);
            ProductInfoVO productInfoVO = new ProductInfoVO();
            BeanUtils.copyProperties(spu, productDetailVO);
            BeanUtils.copyProperties(spu.getSpuInfo(), productInfoVO);
            productDetailVO.setProductInfoVO(productInfoVO);
        }, executor);

        CompletableFuture<Void> goodsVOListFuture = CompletableFuture.runAsync(() -> {
            List<Sku> skuList = skuService.listSkuS(spuId);
            List<GoodsVO> goodsVOList = skuList.stream().map(this::sku2GoodsVO).collect(Collectors.toList());
            productDetailVO.setGoodsVOList(goodsVOList);
        }, executor);

        CompletableFuture.allOf(detailFuture, goodsVOListFuture).get();
        return productDetailVO;
    }

    @Override
    public List<GoodsApiVO> listGoodsApiVOS(Set<Integer> skuIdSet) {
        List<Sku> skuList = skuService.listSkuS(skuIdSet);
        Set<Integer> spuIdSet = skuList.stream().map(Sku::getSpuId).collect(Collectors.toSet());
        List<Spu> spuList = spuService.listSpuS(spuIdSet);
        return skuList.stream().map(sku -> {
            GoodsApiVO goodsApiVO = new GoodsApiVO();
            BeanUtils.copyProperties(sku, goodsApiVO);
            spuList.stream().filter(spu -> sku.getSpuId().equals(spu.getId()))
                    .forEach(spu -> {
                        goodsApiVO.setTitle(spu.getTitle());
                    });
            return goodsApiVO;
        }).collect(Collectors.toList());
    }

    @Override
    public GoodsApiVO getGoodsApiVO(Integer skuId) {
        GoodsApiVO goodsApiVO = new GoodsApiVO();
        Sku sku = skuService.getSku(skuId);
        Spu spu = spuService.getSpu(sku.getSpuId());
        BeanUtils.copyProperties(sku, goodsApiVO);
        goodsApiVO.setTitle(spu.getTitle());
        return goodsApiVO;
    }

    @Override
    public List<ProductVO> listProductVOS(Integer categoryId) {
        List<Spu> spuList = spuService.listSpuS(categoryId);
        return spuList.stream()
                .map(spu -> {
                    ProductVO productVO = new ProductVO();
                    BeanUtils.copyProperties(spu, productVO);
                    productVO.setShowImage(spu.getSpuInfo().getShowImage());
                    return productVO;
                }).collect(Collectors.toList());
    }

    @Override
    public List<ProductApiVO> listProductApiVOS(Integer categoryId) {
        List<Spu> spuList = spuService.listSpuS(categoryId);
        return spuList.stream().map(this::spu2ProductApiVO).collect(Collectors.toList());
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
