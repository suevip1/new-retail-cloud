package com.zhihao.newretail.api.product.feign;

import com.zhihao.newretail.api.product.vo.SpecParamApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "new-retail-product", path = "/product")
public interface SpecParamFeignService {

    /*
    * 分类通用参数列表
    * */
    @GetMapping("/api/specParam/{categoryId}")
    List<SpecParamApiVO> listSpecParamApiVOs(@PathVariable Integer categoryId);

}
