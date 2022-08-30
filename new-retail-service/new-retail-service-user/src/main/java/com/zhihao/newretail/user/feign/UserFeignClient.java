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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class UserFeignClient {

    @Autowired
    private UserService userService;

    @PostMapping("/api/userInfo")
    public UserApiVO getUserApiVO(@RequestBody UserApiDTO userApiDTO) {
        String uuid = userApiDTO.getUuid();
        String username = userApiDTO.getUsername();
        String weChat = userApiDTO.getWeChat();

        if (StringUtils.isEmpty(uuid)
                && StringUtils.isEmpty(username)
                && StringUtils.isEmpty(weChat)) {
            throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "该项不能为空");
        }
        User user = new User();
        BeanUtils.copyProperties(userApiDTO, user);
        return userService.getUserApiVO(user);
    }

    @PostMapping("/api/userInfo/list")
    public List<UserInfoApiVO> listUserInfoApiVOS(@RequestBody Set<Integer> userIdSet) {
        return userService.listUserInfoApiVOS(userIdSet);
    }

}
