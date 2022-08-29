package com.zhihao.newretail.rbac.controller;

import com.zhihao.newretail.api.product.dto.SpecParamAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpecParamUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.SpecParamApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
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
        List<SpecParamApiVO> specParamApiVOList = specParamService.listSpecParamApiVOS(categoryId);
        UserLoginContext.sysClean();
        if (!CollectionUtils.isEmpty(specParamApiVOList)) {
            return R.ok().put("data", specParamApiVOList);
        } else {
            return R.error(HttpStatus.SC_NO_CONTENT, "暂无数据").put("data", specParamApiVOList);
        }
    }

    @RequiresLogin
    @PostMapping("/specParam")
    public R specParam(@Valid @RequestBody SpecParamAddApiDTO specParamAddApiDTO) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer insertRow = specParamService.addSpecParam(specParamAddApiDTO);
        UserLoginContext.sysClean();
        if (insertRow == null) {
            throw new ServiceException("商品服务繁忙");
        }
        if (insertRow <= 0) {
            throw new ServiceException("新增商品分类通用参数失败");
        }
        return R.ok();
    }

    @RequiresLogin
    @PutMapping("/specParam/{specParamId}")
    public R specParam(@PathVariable Integer specParamId, @Valid @RequestBody SpecParamUpdateApiDTO specParamUpdateApiDTO) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer updateRow = specParamService.updateSpecParam(specParamId, specParamUpdateApiDTO);
        UserLoginContext.sysClean();
        if (updateRow == null) {
            throw new ServiceException("商品服务繁忙");
        }
        if (updateRow <= 0) {
            throw new ServiceException("修改商品分类通用参数失败");
        }
        return R.ok();
    }

    @RequiresLogin
    @DeleteMapping("/specParam/{specParamId}")
    public R specParam(@PathVariable Integer specParamId) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer deleteRow = specParamService.deleteSpecParam(specParamId);
        UserLoginContext.sysClean();
        if (deleteRow == null) {
            throw new ServiceException("商品服务繁忙");
        }
        if (deleteRow <= 0) {
            throw new ServiceException("删除商品分类通用参数失败");
        }
        return R.ok();
    }

}
