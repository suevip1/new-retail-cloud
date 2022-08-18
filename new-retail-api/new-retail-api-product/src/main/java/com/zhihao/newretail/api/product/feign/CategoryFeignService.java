package com.zhihao.newretail.api.product.feign;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "new-retail-product", path = "/product")
public interface CategoryFeignService {

    /*
    * 新增商品分类
    * */
    @PostMapping("/api/category")
    void addCategory(@RequestBody CategoryAddApiDTO categoryAddApiDTO);

    /*
    * 修改商品分类
    * */
    @PutMapping("/api/category/{categoryId}")
    void updateCategory(@PathVariable Integer categoryId,
                        @RequestBody CategoryUpdateApiDTO categoryUpdateApiDTO);

}
