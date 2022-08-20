package com.zhihao.newretail.api.product.feign;

import com.zhihao.newretail.api.product.vo.GoodsApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@FeignClient(name = "new-retail-product", path = "/product")
public interface ProductFeignService {

    @PostMapping("/api/goods/list")
    List<GoodsApiVO> listGoodsApiVOS(@RequestBody Set<Integer> idSet);

    @GetMapping("/api/goods/{skuId}")
    GoodsApiVO getGoodsApiVO(@PathVariable Integer skuId);

}
