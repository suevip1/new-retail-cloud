package com.zhihao.newretail.rbac.controller;

import com.zhihao.newretail.api.product.dto.CategoryDTO;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.rbac.context.SysUserTokenContext;
import com.zhihao.newretail.rbac.service.SysCategoryService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class SysCategoryController {

    @Autowired
    private SysCategoryService categoryService;

    @RequiresLogin
    @PostMapping("/category")
    public R addCategory(@RequestBody CategoryDTO categoryDTO) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        categoryService.addCategory(categoryDTO);
        UserLoginContext.sysClean();
        return R.ok();
    }

}
