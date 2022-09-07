package com.zhihao.newretail.rbac.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.rbac.context.SysUserTokenContext;
import com.zhihao.newretail.rbac.service.SysSlideService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class SysSlideController {

    @Autowired
    private SysSlideService sysSlideService;

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
