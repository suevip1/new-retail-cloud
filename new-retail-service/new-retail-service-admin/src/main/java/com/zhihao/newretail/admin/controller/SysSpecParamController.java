package com.zhihao.newretail.admin.controller;

import com.zhihao.newretail.api.product.dto.SpecParamAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpecParamUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.SpecParamApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.admin.context.SysUserTokenContext;
import com.zhihao.newretail.admin.service.SysSpecParamService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SysSpecParamController {

    @Autowired
    private SysSpecParamService specParamService;

    @RequiresLogin
    @GetMapping("/spec-param/{categoryId}")
    public R specParamList(@PathVariable Integer categoryId) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        List<SpecParamApiVO> specParamApiVOList = specParamService.listSpecParamApiVOS(categoryId);
        UserLoginContext.sysClean();
        if (!CollectionUtils.isEmpty(specParamApiVOList)) {
            return R.ok().put("data", specParamApiVOList);
        }
        return R.error(HttpStatus.SC_NO_CONTENT, "暂无数据").put("data", specParamApiVOList);
    }

    @RequiresLogin
    @PostMapping("/spec-param")
    public R specParam(@Valid @RequestBody SpecParamAddApiDTO specParamAddApiDTO) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer insertRow = specParamService.addSpecParam(specParamAddApiDTO);
        UserLoginContext.sysClean();
        if (insertRow != null) {
            if (insertRow >= 1) {
                return R.ok("新增商品分类通用参数成功");
            }
            return R.error("新增商品分类通用参数失败");
        }
        throw new ServiceException("商品服务繁忙");
    }

    @RequiresLogin
    @PutMapping("/spec-param/{specParamId}")
    public R specParam(@PathVariable Integer specParamId, @Valid @RequestBody SpecParamUpdateApiDTO specParamUpdateApiDTO) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer updateRow = specParamService.updateSpecParam(specParamId, specParamUpdateApiDTO);
        UserLoginContext.sysClean();
        if (updateRow != null) {
            if (updateRow >= 1) {
                return R.ok("修改商品分类通用参数成功");
            }
            return R.error("修改商品分类通用参数失败");
        }
        throw new ServiceException("商品服务繁忙");
    }

    @RequiresLogin
    @DeleteMapping("/spec-param/{specParamId}")
    public R specParam(@PathVariable Integer specParamId) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer deleteRow = specParamService.deleteSpecParam(specParamId);
        UserLoginContext.sysClean();
        if (deleteRow != null) {
            if (deleteRow >= 1) {
                return R.ok("删除商品分类通用参数成功");
            }
            return R.error("删除商品分类通用参数失败");
        }
        throw new ServiceException("商品服务繁忙");
    }

}
