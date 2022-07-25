package com.zhihao.newretail.user.feign;

import com.zhihao.newretail.api.user.dto.UserApiDTO;
import com.zhihao.newretail.api.user.feign.UserFeignService;
import com.zhihao.newretail.api.user.vo.UserApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.user.pojo.User;
import com.zhihao.newretail.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserFeignController implements UserFeignService {

    @Autowired
    private UserService userService;

    @Override
    public UserApiVO getUserInfo(UserApiDTO userApiDTO) {
        String uuid = userApiDTO.getUuid();
        String username = userApiDTO.getUsername();
        String weChat = userApiDTO.getWeChat();

        if (StringUtils.isEmpty(uuid)
                && StringUtils.isEmpty(username)
                && StringUtils.isEmpty(weChat))
            throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "该项不能为空");

        User user = new User();
        user.setUuid(uuid);
        user.setUsername(username);
        user.setWeChat(weChat);
        UserApiVO userApiVO = userService.getUserApiVO(user);

        if (ObjectUtils.isEmpty(userApiVO))
            throw new ServiceException(HttpStatus.SC_NOT_FOUND, "用户不存在");

        return userApiVO;
    }

}
