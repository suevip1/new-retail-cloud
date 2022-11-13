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
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SysCategoryController {

    @Autowired
    private SysCategoryService categoryService;

    @RequiresLogin
    @GetMapping("/category/list")
    public R categories(@RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize) {
        if (pageNum != 0 && pageSize != 0) {
            String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
            SysUserTokenContext.setUserToken(userToken);
            PageUtil<CategoryApiVO> pageData = categoryService.listCategoryApiVOS(pageNum, pageSize);
            UserLoginContext.sysClean();
            if (!CollectionUtils.isEmpty(pageData.getList())) {
                return R.ok().put("data", pageData);
            }
            return R.error(HttpStatus.SC_NO_CONTENT, "暂无数据").put("data", pageData);
        } else {
            UserLoginContext.sysClean();
            throw new ServiceException("分页参数不能为0");
        }
    }

    @RequiresLogin
    @GetMapping("/category/{categoryId}")
    public R categoryInfo(@PathVariable Integer categoryId) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        CategoryApiVO categoryApiVO = categoryService.getCategoryApiVO(categoryId);
        UserLoginContext.sysClean();
        if (!ObjectUtils.isEmpty(categoryApiVO.getId())) {
            return R.ok().put("data", categoryApiVO);
        }
        return R.error(HttpStatus.SC_NOT_FOUND, "分类信息不存在").put("data", categoryApiVO);
    }

    @RequiresLogin
    @PostMapping("/category")
    public R category(@Valid @RequestBody CategoryAddApiDTO categoryAddApiDTO) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer insertRow = categoryService.addCategory(categoryAddApiDTO);
        UserLoginContext.sysClean();
        if (insertRow != null) {
            if (insertRow >= 1) {
                return R.ok("新增商品分类成功");
            }
            return R.error("新增商品分类失败");
        }
        throw new ServiceException("商品服务繁忙");
    }

    @RequiresLogin
    @PutMapping("/category/{categoryId}")
    public R category(@PathVariable Integer categoryId, @Valid @RequestBody CategoryUpdateApiDTO categoryUpdateApiDTO) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer updateRow = categoryService.updateCategory(categoryId, categoryUpdateApiDTO);
        UserLoginContext.sysClean();
        if (updateRow != null) {
            if (updateRow >= 1) {
                return R.ok("修改商品分类成功");
            }
            return R.error("修改商品分类失败");
        }
        throw new ServiceException("商品服务繁忙");
    }

    @RequiresLogin
    @DeleteMapping("/category/{categoryId}")
    public R category(@PathVariable Integer categoryId) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer deleteRow = categoryService.deleteCategory(categoryId);
        UserLoginContext.sysClean();
        if (deleteRow != null) {
            if (deleteRow >= 1) {
                return R.ok("删除商品分类成功");
            }
            return R.error("删除商品分类失败");
        }
        throw new ServiceException("商品服务繁忙");
    }

}
