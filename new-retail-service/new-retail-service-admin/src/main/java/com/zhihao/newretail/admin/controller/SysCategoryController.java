package com.zhihao.newretail.admin.controller;

import com.zhihao.newretail.api.product.dto.CategoryAddApiDTO;
import com.zhihao.newretail.api.product.dto.CategoryUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.CategoryApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.admin.context.SysUserTokenContext;
import com.zhihao.newretail.admin.service.SysCategoryService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class SysCategoryController {

    @Autowired
    private SysCategoryService categoryService;

    @RequiresLogin
    @GetMapping("/category/list")
    public R categories(@RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize) {
        if (pageNum == 0 || pageSize == 0) {
            throw new ServiceException("分页参数不能为0");
        }
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        PageUtil<CategoryApiVO> pageData = categoryService.listCategoryApiVOS(pageNum, pageSize);
        UserLoginContext.sysClean();
        return R.ok().put("data", pageData);
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
    public R category(@Valid @RequestBody CategoryAddApiDTO categoryAddApiDTO) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer insertRow = categoryService.addCategory(categoryAddApiDTO);
        UserLoginContext.sysClean();
        if (insertRow == null) {
            throw new ServiceException("商品服务繁忙");
        }
        if (insertRow <= 0) {
            throw new ServiceException("新增商品分类失败");
        }
        return R.ok();
    }

    @RequiresLogin
    @PutMapping("/category/{categoryId}")
    public R category(@PathVariable Integer categoryId, @Valid @RequestBody CategoryUpdateApiDTO categoryUpdateApiDTO) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer updateRow = categoryService.updateCategory(categoryId, categoryUpdateApiDTO);
        UserLoginContext.sysClean();
        if (updateRow == null) {
            throw new ServiceException("商品服务繁忙");
        }
        if (updateRow <= 0) {
            throw new ServiceException("修改商品分类失败");
        }
        return R.ok();
    }

    @RequiresLogin
    @DeleteMapping("/category/{categoryId}")
    public R category(@PathVariable Integer categoryId) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer deleteRow = categoryService.deleteCategory(categoryId);
        UserLoginContext.sysClean();
        if (deleteRow == null) {
            throw new ServiceException("商品服务繁忙");
        }
        if (deleteRow <= 0) {
            throw new ServiceException("删除商品分类失败");
        }
        return R.ok();
    }

}
