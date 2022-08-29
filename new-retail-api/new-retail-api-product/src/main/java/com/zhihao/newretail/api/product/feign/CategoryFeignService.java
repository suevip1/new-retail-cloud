package com.zhihao.newretail.api.product.feign;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;
import com.zhihao.newretail.api.product.fallback.CategoryFeignFallback;
import com.zhihao.newretail.api.product.vo.CategoryApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Primary
@FeignClient(name = "new-retail-product", path = "/product", fallback = CategoryFeignFallback.class)
public interface CategoryFeignService {

    /*
    * 商品分类列表
    * */
    @GetMapping("/api/category/list")
    List<CategoryApiVO> listCategoryApiVOS();

    /*
    * 商品分类信息
    * */
    @GetMapping("/api/category/{categoryId}")
    CategoryApiVO getCategoryApiVO(@PathVariable Integer categoryId);

    /*
    * 新增商品分类
    * */
    @PostMapping("/api/category")
    Integer addCategory(@RequestBody CategoryAddApiDTO categoryAddApiDTO);

    /*
    * 修改商品分类
    * */
    @PutMapping("/api/category/{categoryId}")
    Integer updateCategory(@PathVariable Integer categoryId, @RequestBody CategoryUpdateApiDTO categoryUpdateApiDTO);

    /*
    * 删除商品分类
    * */
    @DeleteMapping("/api/category/{categoryId}")
    Integer deleteCategory(@PathVariable Integer categoryId);

}
