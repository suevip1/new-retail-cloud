package com.zhihao.newretail.user.controller;

import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.security.context.UserLoginContext;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.user.pojo.dto.UpdateNickNameDTO;
import com.zhihao.newretail.user.pojo.dto.UserRegisterDTO;
import com.zhihao.newretail.user.pojo.vo.UserInfoVO;
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
    public R register(@RequestBody UserRegisterDTO userRegisterDTO) {
        Integer insertRow = userService.insertUser(userRegisterDTO);
        if (insertRow <= 0 || ObjectUtils.isEmpty(insertRow)) {
            throw new ServiceException("注册失败");
        } else {
            return R.ok("注册成功");
        }
    }

    @RequiresLogin
    @GetMapping("/userInfo")
    public R userInfo() {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        UserInfoVO userInfoVO = userService.getUserInfoVO(userId);
        UserLoginContext.clean();

        return R.ok().put("data", userInfoVO);
    }

    @RequiresLogin
    @PutMapping("/userInfo/photo")
    public R userInfo(@RequestPart MultipartFile file) throws IOException {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        UserInfoVO userInfoVO = userService.updateUserInfo(userId, file);
        UserLoginContext.clean();

        return R.ok().put("data", userInfoVO);
    }

    @RequiresLogin
    @PutMapping("/userInfo/nickName")
    public R userInfo(@Valid @RequestBody UpdateNickNameDTO updateNickNameDTO) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        UserInfoVO userInfoVO = userService.updateUserInfo(userId, updateNickNameDTO);
        UserLoginContext.clean();

        return R.ok().put("data", userInfoVO);
    }

}
