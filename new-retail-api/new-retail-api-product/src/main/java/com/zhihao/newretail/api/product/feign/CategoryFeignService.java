package com.zhihao.newretail.api.product.feign;

import com.zhihao.newretail.api.product.dto.CategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "new-retail-product", path = "/product")
public interface CategoryFeignService {

    /*
    * 新增商品分类
    * */
    @PostMapping("/api/category")
    void addCategory(@RequestBody CategoryDTO categoryDTO);

}
