package com.zhihao.newretail.rbac.controller;

import com.zhihao.newretail.api.product.dto.SpecParamAddApiDTO;
import com.zhihao.newretail.api.product.vo.SpecParamApiVO;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.rbac.context.SysUserTokenContext;
import com.zhihao.newretail.rbac.service.SysSpecParamService;
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
        if (!CollectionUtils.isEmpty(specParamApiVOList)) {
            return R.ok().put("data", specParamApiVOList);
        } else {
            return R.error(HttpStatus.SC_NO_CONTENT, "暂无数据").put("data", specParamApiVOList);
        }
    }

    @RequiresLogin
    @PostMapping("/specParam")
    public R specParamAdd(@Valid @RequestBody SpecParamAddApiDTO specParamAddApiDTO) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        specParamService.addSpecParam(specParamAddApiDTO);
        UserLoginContext.sysClean();
        return R.ok();
    }

}
