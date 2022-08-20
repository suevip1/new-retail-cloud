package com.zhihao.newretail.rbac.controller;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.CategoryApiVO;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.rbac.context.SysUserTokenContext;
import com.zhihao.newretail.rbac.service.SysCategoryService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class SysCategoryController {

    @Autowired
    private SysCategoryService categoryService;

    @RequiresLogin
    @GetMapping("/category/list")
    public R categories() {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        List<CategoryApiVO> categoryApiVOList = categoryService.listCategoryApiVOS();
        UserLoginContext.sysClean();
        if (!CollectionUtils.isEmpty(categoryApiVOList)) {
            return R.ok().put("data", categoryApiVOList);
        } else {
            return R.error(HttpStatus.SC_NO_CONTENT, "暂无数据").put("data", categoryApiVOList);
        }
    }

    @RequiresLogin
    @GetMapping("/category/{categoryId}")
    public R categoryInfo(@PathVariable Integer categoryId) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        CategoryApiVO categoryApiVO = categoryService.getCategoryApiVO(categoryId);
        UserLoginContext.sysClean();
        return R.ok().put("data", categoryApiVO);
    }

    @RequiresLogin
    @PostMapping("/category")
    public R addCategory(@Valid @RequestBody CategoryAddApiDTO categoryAddApiDTO) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        categoryService.addCategory(categoryAddApiDTO);
        UserLoginContext.sysClean();
        return R.ok();
    }

    @RequiresLogin
    @PutMapping("/category/{categoryId}")
    public R updateCategory(@PathVariable Integer categoryId, @Valid @RequestBody CategoryUpdateApiDTO categoryUpdateApiDTO) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        categoryService.updateCategory(categoryId, categoryUpdateApiDTO);
        UserLoginContext.sysClean();
        return R.ok();
    }

    @RequiresLogin
    @DeleteMapping("/category/{categoryId}")
    public R deleteCategory(@PathVariable Integer categoryId) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        categoryService.deleteCategory(categoryId);
        UserLoginContext.sysClean();
        return R.ok();
    }

}
