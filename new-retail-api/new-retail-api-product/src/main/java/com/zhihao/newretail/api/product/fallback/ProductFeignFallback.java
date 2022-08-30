package com.zhihao.newretail.api.product.fallback;

import com.zhihao.newretail.api.product.feign.ProductFeignService;
import com.zhihao.newretail.api.product.vo.GoodsApiVO;
import com.zhihao.newretail.api.product.vo.ProductApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/*
* 熔断保护
* 上级服务出问题执行该实现类
* */
@Component
public class ProductFeignFallback implements ProductFeignService {

    @Override
    public List<GoodsApiVO> listGoodsApiVOS(Set<Integer> idSet) {
        return null;
    }

    @Override
    public GoodsApiVO getGoodsApiVO(Integer skuId) {
        return null;
    }

    @Override
    public PageUtil<ProductApiVO> listProductApiVOS(Integer categoryId, Integer pageNum, Integer pageSize) {
        return null;
    }

}
