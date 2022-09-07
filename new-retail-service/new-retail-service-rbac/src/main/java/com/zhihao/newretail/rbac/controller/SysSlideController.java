package com.zhihao.newretail.rbac.controller;

import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.rbac.context.SysUserTokenContext;
import com.zhihao.newretail.rbac.form.SlideForm;
import com.zhihao.newretail.rbac.service.SysSlideService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class SysSlideController {

    @Autowired
    private SysSlideService sysSlideService;

    @RequiresLogin
    @PostMapping("/slide")
    public R slide(@Valid @RequestBody SlideForm form) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer insertRow = sysSlideService.insertSlide(form);
        UserLoginContext.sysClean();
        if (insertRow == null) {
            throw new ServiceException("商品服务繁忙");
        }
        if (insertRow <= 0) {
            throw new ServiceException("新增轮播图失败");
        }
        return R.ok();
    }

    @RequiresLogin
    @PutMapping("/slide/{slideId}")
    public R slide(@PathVariable Integer slideId, @Valid @RequestBody SlideForm form) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer updateRow = sysSlideService.updateSlide(slideId, form);
        UserLoginContext.sysClean();
        if (updateRow == null) {
            throw new ServiceException("商品服务繁忙");
        }
        if (updateRow <= 0) {
            throw new ServiceException("修改轮播图失败");
        }
        return R.ok();
    }

    @RequiresLogin
    @PostMapping("/slide/upload/image")
    public R uploadSlideImage(@RequestPart MultipartFile file) throws IOException {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        String imageUrl = sysSlideService.uploadSlideImage(file);
        UserLoginContext.sysClean();
        return R.ok().put("data", imageUrl);
    }

}
