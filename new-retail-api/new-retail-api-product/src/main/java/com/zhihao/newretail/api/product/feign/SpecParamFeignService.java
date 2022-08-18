package com.zhihao.newretail.api.product.feign;

import com.zhihao.newretail.api.product.dto.SpecParamAddApiDTO;
import com.zhihao.newretail.api.product.vo.SpecParamApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "new-retail-product", path = "/product")
public interface SpecParamFeignService {

    /*
    * 分类通用参数列表
    * */
    @GetMapping("/api/specParam/{categoryId}")
    List<SpecParamApiVO> listSpecParamApiVOs(@PathVariable Integer categoryId);

    /*
    * 新增分类参数
    * */
    @PostMapping("/api/specParam")
    void addSpecParam(@Valid @RequestBody SpecParamAddApiDTO specParamAddApiDTO);

}
