package com.zhihao.newretail.user.feign;

import com.zhihao.newretail.api.user.dto.UserApiDTO;
import com.zhihao.newretail.api.user.vo.UserApiVO;
import com.zhihao.newretail.api.user.vo.UserInfoApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.user.pojo.User;
import com.zhihao.newretail.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/feign")
public class UserFeignClient {

    @Autowired
    private UserService userService;

    @PostMapping("/userInfo")
    public UserApiVO getUserApiVO(@RequestBody UserApiDTO userApiDTO) {
        if (!StringUtils.isEmpty(userApiDTO.getWeChat())) {
            return userService.aliPayUserIdGetUserApiVO(userApiDTO);
        } else if (!StringUtils.isEmpty(userApiDTO.getUsername())) {
            User user = new User();
            BeanUtils.copyProperties(userApiDTO, user);
            return userService.getUserApiVO(user);
        } else {
            throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "该项不能为空");
        }
    }

    @GetMapping("/userInfo/{userId}")
    public UserInfoApiVO getUserInfoApiVO(@PathVariable Integer userId) {
        return userService.getUserInfoApiVO(userId);
    }

    @PostMapping("/userInfo/list")
    public List<UserInfoApiVO> listUserInfoApiVOS(@RequestBody Set<Integer> userIdSet) {
        return userService.listUserInfoApiVOS(userIdSet);
    }

}
