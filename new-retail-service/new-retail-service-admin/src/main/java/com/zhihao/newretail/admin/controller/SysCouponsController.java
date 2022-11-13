package com.zhihao.newretail.admin.controller;

import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.admin.context.SysUserTokenContext;
import com.zhihao.newretail.admin.form.CouponsForm;
import com.zhihao.newretail.admin.service.SysCouponsService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SysCouponsController {

    @Autowired
    private SysCouponsService sysCouponsService;

    @RequiresLogin
    @GetMapping("/coupons/list")
    public R couponsList(@RequestParam(required = false) Integer saleable,
                         @RequestParam(defaultValue = "1") Integer pageNum,
                         @RequestParam(defaultValue = "10") Integer pageSize) {
        if (pageNum != 0 && pageSize != 0) {
            String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
            SysUserTokenContext.setUserToken(userToken);
            PageUtil<CouponsApiVO> pageData = sysCouponsService.listCouponsApiVOS(saleable, pageNum, pageSize);
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
    @PostMapping("/coupons")
    public R couponsAdd(@Valid @RequestBody CouponsForm form) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer insertRow = sysCouponsService.insertCoupons(form);
        UserLoginContext.sysClean();
        if (insertRow != null) {
            if (insertRow >= 1) {
                return R.ok("新增优惠券成功");
            }
            return R.error("新增优惠券失败");
        }
        throw new ServiceException("优惠券服务繁忙");
    }

    @RequiresLogin
    @PutMapping("/coupons/{couponsId}")
    public R couponsUpdate(@PathVariable Integer couponsId, @Valid @RequestBody CouponsForm form) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer updateRow = sysCouponsService.updateCoupons(couponsId, form);
        UserLoginContext.sysClean();
        if (updateRow != null) {
            if (updateRow >= 1) {
                return R.ok("修改优惠券成功");
            }
            return R.error("修改优惠券失败");
        }
        throw new ServiceException("优惠券服务繁忙");
    }

    @RequiresLogin
    @DeleteMapping("/coupons/{couponsId}")
    public R couponsDelete(@PathVariable Integer couponsId) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer deleteRow = sysCouponsService.deleteCoupons(couponsId);
        UserLoginContext.sysClean();
        if (deleteRow != null) {
            if (deleteRow >= 1) {
                return R.ok("删除优惠券成功");
            }
            return R.error("删除优惠券失败");
        }
        throw new ServiceException("优惠券服务繁忙");
    }

}
