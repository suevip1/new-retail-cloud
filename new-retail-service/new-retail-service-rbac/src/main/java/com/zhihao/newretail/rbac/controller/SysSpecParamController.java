package com.zhihao.newretail.rbac.controller;

import com.zhihao.newretail.api.product.vo.SpecParamApiVO;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.rbac.context.SysUserTokenContext;
import com.zhihao.newretail.rbac.service.SysSpecParamService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class SysSpecParamController {

    @Autowired
    private SysSpecParamService specParamService;

    @RequiresLogin
    @GetMapping("/specParam/{categoryId}")
    public R specParamList(@PathVariable Integer categoryId) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        List<SpecParamApiVO> specParamApiVOList = specParamService.listSpecParamApiVOs(categoryId);
        UserLoginContext.sysClean();
        return R.ok().put("data", specParamApiVOList);
    }

}
