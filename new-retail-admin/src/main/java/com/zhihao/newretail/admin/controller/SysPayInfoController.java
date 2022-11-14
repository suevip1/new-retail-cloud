package com.zhihao.newretail.admin.controller;

import com.zhihao.newretail.api.pay.vo.PayInfoApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.admin.context.SysUserTokenContext;
import com.zhihao.newretail.admin.service.SysPayInfoService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysPayInfoController {

    @Autowired
    private SysPayInfoService sysPayInfoService;

    @RequiresLogin
    @GetMapping("/pay-info/list")
    public R payInfoList(@RequestParam(required = false) Long orderId,
                         @RequestParam(required = false) Integer userId,
                         @RequestParam(required = false) Integer payPlatform,
                         @RequestParam(required = false) Integer status,
                         @RequestParam(required = false) Integer platformNumber,
                         @RequestParam(defaultValue = "1") Integer pageNum,
                         @RequestParam(defaultValue = "10") Integer pageSize) {
        if (pageNum != 0 && pageSize != 0) {
            String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
            SysUserTokenContext.setUserToken(userToken);
            PageUtil<PayInfoApiVO> pageData = sysPayInfoService.listPayInfoApiVOS(orderId, userId, payPlatform, status, platformNumber, pageNum, pageSize);
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

}
