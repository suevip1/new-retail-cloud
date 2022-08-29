package com.zhihao.newretail.api.product.feign;

import com.zhihao.newretail.api.product.dto.SpecParamAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpecParamUpdateApiDTO;
import com.zhihao.newretail.api.product.fallback.SpecParamFeignFallback;
import com.zhihao.newretail.api.product.vo.SpecParamApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Primary
@FeignClient(name = "new-retail-product", path = "/product", fallback = SpecParamFeignFallback.class)
public interface SpecParamFeignService {

    /*
    * 分类通用参数列表
    * */
    @GetMapping("/api/specParam/{categoryId}")
    List<SpecParamApiVO> listSpecParamApiVOS(@PathVariable Integer categoryId);

    /*
    * 新增分类参数
    * */
    @PostMapping("/api/specParam")
    Integer addSpecParam(@RequestBody SpecParamAddApiDTO specParamAddApiDTO);

    /*
    * 修改分类参数
    * */
    @PutMapping("/api/specParam/{specParamId}")
    Integer updateSpecParam(@PathVariable Integer specParamId, @RequestBody SpecParamUpdateApiDTO specParamUpdateApiDTO);

    /*
    * 删除分类参数
    * */
    @DeleteMapping("/api/specParam/{specParamId}")
    Integer deleteSpecParam(@PathVariable Integer specParamId);

}
