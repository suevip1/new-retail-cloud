package com.zhihao.newretail.user.controller;

import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.security.context.UserLoginContext;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.user.form.UserRegisterForm;
import com.zhihao.newretail.user.form.UpdateNickNameForm;
import com.zhihao.newretail.user.vo.UserInfoVO;
import com.zhihao.newretail.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public R register(@Valid @RequestBody UserRegisterForm form) {
        Integer insertRow = userService.insertUser(form);
        if (insertRow <= 0 || ObjectUtils.isEmpty(insertRow)) {
            throw new ServiceException("注册失败");
        } else {
            return R.ok("注册成功");
        }
    }

    @RequiresLogin
    @GetMapping("/info")
    public R userInfo() {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        UserInfoVO userInfoVO = userService.getUserInfoVO(userId);
        UserLoginContext.clean();

        return R.ok().put("data", userInfoVO);
    }

    @RequiresLogin
    @PutMapping("/info/photo")
    public R userInfo(@RequestPart MultipartFile file) throws IOException {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        UserInfoVO userInfoVO = userService.updateUserInfo(userId, file);
        UserLoginContext.clean();

        return R.ok().put("data", userInfoVO);
    }

    @RequiresLogin
    @PutMapping("/info/nickName")
    public R userInfo(@Valid @RequestBody UpdateNickNameForm form) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        UserInfoVO userInfoVO = userService.updateUserInfo(userId, form);
        UserLoginContext.clean();

        return R.ok().put("data", userInfoVO);
    }

}
