package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.api.product.vo.SkuApiVO;
import com.zhihao.newretail.core.enums.DeleteEnum;
import com.zhihao.newretail.product.dao.SkuMapper;
import com.zhihao.newretail.product.dao.SpuMapper;
import com.zhihao.newretail.product.enums.ProductEnum;
import com.zhihao.newretail.product.pojo.Sku;
import com.zhihao.newretail.product.pojo.Spu;
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
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

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
    public List<SkuApiVO> listSkuApiVOs(Set<Integer> skuIdSet) {
        List<Sku> skuList = skuMapper.selectListByIdSet(skuIdSet);
        Set<Integer> spuIdSet = skuList.stream().map(Sku::getSpuId).collect(Collectors.toSet());
        List<Spu> spuList = spuMapper.selectListByIdSet(spuIdSet);
        return skuList.stream().map(sku -> {
            SkuApiVO skuApiVO = new SkuApiVO();
            BeanUtils.copyProperties(sku, skuApiVO);
            spuList.stream().filter(spu -> sku.getSpuId().equals(spu.getId()))
                    .forEach(spu -> {
                        skuApiVO.setTitle(spu.getTitle());
                    });
            return skuApiVO;
        }).collect(Collectors.toList());
    }

    @Override
    public SkuApiVO getSkuApiVO(Integer skuId) {
        SkuApiVO skuApiVO = new SkuApiVO();
        Sku sku = skuMapper.selectByPrimaryKey(skuId);

        if (ObjectUtils.isEmpty(sku)
                || DeleteEnum.DELETE.getCode().equals(sku.getIsDelete())
                || ProductEnum.NOT_SALEABLE.getCode().equals(sku.getIsSaleable())) {
            return skuApiVO;
        } else {
            BeanUtils.copyProperties(sku, skuApiVO);
            return skuApiVO;
        }
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

    private GoodsVO sku2GoodsVO(Sku sku) {
        GoodsVO goodsVO = new GoodsVO();
        BeanUtils.copyProperties(sku, goodsVO);
        return goodsVO;
    }

}
