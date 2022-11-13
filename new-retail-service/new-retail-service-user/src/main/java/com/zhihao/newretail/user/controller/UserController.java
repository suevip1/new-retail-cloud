package com.zhihao.newretail.user.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.security.context.UserLoginContext;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.user.form.UserRegisterForm;
import com.zhihao.newretail.user.form.UpdateNickNameForm;
import com.zhihao.newretail.user.vo.UserInfoVO;
import com.zhihao.newretail.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public R register(@Valid @RequestBody UserRegisterForm form) {
        int insertUserRow = userService.insertUser(form);
        if (insertUserRow >= 1) {
            return R.ok("注册成功");
        }
        return R.error("注册失败");
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
    public R updatePhoto(@RequestPart MultipartFile file) throws IOException {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        int updateRow = userService.updateUserPhoto(userId, file);
        UserLoginContext.clean();
        if (updateRow >= 1) {
            return R.ok("修改成功");
        }
        return R.error("修改失败");
    }

    @RequiresLogin
    @PutMapping("/info/nickName")
    public R updateNickName(@Valid @RequestBody UpdateNickNameForm form) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        int updateRow = userService.updateUserNickName(userId, form);
        UserLoginContext.clean();
        if (updateRow >= 1) {
            return R.ok("修改成功");
        }
        return R.error("修改失败");
    }

}
