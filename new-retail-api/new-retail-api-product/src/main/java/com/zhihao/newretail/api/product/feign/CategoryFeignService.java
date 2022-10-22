package com.zhihao.newretail.api.product.feign;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;
import com.zhihao.newretail.api.product.fallback.CategoryFeignFallback;
import com.zhihao.newretail.api.product.vo.CategoryApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.*;

@Primary
@FeignClient(name = "new-retail-product", path = "/product", fallback = CategoryFeignFallback.class)
public interface CategoryFeignService {

    /*
    * 商品分类列表
    * */
    @GetMapping("/feign/category/list")
    PageUtil<CategoryApiVO> listCategoryApiVOS(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize);

    /*
    * 商品分类信息
    * */
    @GetMapping("/feign/category/{categoryId}")
    CategoryApiVO getCategoryApiVO(@PathVariable Integer categoryId);

    /*
    * 新增商品分类
    * */
    @PostMapping("/feign/category")
    Integer addCategory(@RequestBody CategoryAddApiDTO categoryAddApiDTO);

    /*
    * 修改商品分类
    * */
    @PutMapping("/feign/category/{categoryId}")
    Integer updateCategory(@PathVariable Integer categoryId, @RequestBody CategoryUpdateApiDTO categoryUpdateApiDTO);

    /*
    * 删除商品分类
    * */
    @DeleteMapping("/feign/category/{categoryId}")
    Integer deleteCategory(@PathVariable Integer categoryId);

}
