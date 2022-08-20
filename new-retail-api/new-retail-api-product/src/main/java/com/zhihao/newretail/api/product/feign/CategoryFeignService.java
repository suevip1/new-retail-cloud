package com.zhihao.newretail.api.product.feign;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.CategoryApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "new-retail-product", path = "/product")
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
    void addCategory(@Valid @RequestBody CategoryAddApiDTO categoryAddApiDTO);

    /*
    * 修改商品分类
    * */
    @PutMapping("/api/category/{categoryId}")
    void updateCategory(@PathVariable Integer categoryId,
                        @Valid @RequestBody CategoryUpdateApiDTO categoryUpdateApiDTO);

    /*
    * 删除商品分类
    * */
    @DeleteMapping("/api/category/{categoryId}")
    void deleteCategory(@PathVariable Integer categoryId);

}
