package com.zhihao.newretail.rbac.controller;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.rbac.context.SysUserTokenContext;
import com.zhihao.newretail.rbac.service.SysCategoryService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class SysCategoryController {

    @Autowired
    private SysCategoryService categoryService;

    @RequiresLogin
    @PostMapping("/category")
    public R addCategory(@RequestBody CategoryAddApiDTO categoryAddApiDTO) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        categoryService.addCategory(categoryAddApiDTO);
        UserLoginContext.sysClean();
        return R.ok();
    }

    @RequiresLogin
    @PutMapping("/category/{categoryId}")
    public R updateCategory(@PathVariable Integer categoryId, @RequestBody CategoryUpdateApiDTO categoryUpdateApiDTO) {
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
