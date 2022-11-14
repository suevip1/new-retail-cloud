package com.zhihao.newretail.user.feign;

import com.zhihao.newretail.api.user.dto.UserApiDTO;
import com.zhihao.newretail.api.user.vo.UserApiVO;
import com.zhihao.newretail.api.user.vo.UserInfoApiVO;
import com.zhihao.newretail.user.pojo.User;
import com.zhihao.newretail.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/feign")
public class UserFeignClient {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public UserApiVO getUserApiVO(@RequestBody UserApiDTO userApiDTO) {
        if (!StringUtils.isEmpty(userApiDTO.getWeChat())) {
            return userService.getUserApiVOByAliPayUserId(userApiDTO);
        } else if (!StringUtils.isEmpty(userApiDTO.getUsername())) {
            User user = new User();
            BeanUtils.copyProperties(userApiDTO, user);
            return userService.getUserApiVO(user);
        } else {
            return null;
        }
    }

    @GetMapping("/info/{userId}")
    public UserInfoApiVO getUserInfoApiVO(@PathVariable Integer userId) {
        return userService.getUserInfoApiVO(userId);
    }

    @PostMapping("/info/list")
    public List<UserInfoApiVO> listUserInfoApiVOS(@RequestBody Set<Integer> userIdSet) {
        return userService.listUserInfoApiVOS(userIdSet);
    }

}
