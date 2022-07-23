package com.zhihao.newretail.user.service.impl;

import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.SnowflakeIdWorker;
import com.zhihao.newretail.user.dao.UserMapper;
import com.zhihao.newretail.user.pojo.User;
import com.zhihao.newretail.user.pojo.dto.UserRegisterDTO;
import com.zhihao.newretail.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /* 密码md5加盐 */
    private final static String PASSWORD_SECRET = "df71e87067b84af687821199de5fc831";

    /*
     * 新增用户
     * 基于用户名、密码注册
     * */
    @Override
    public int insertUser(UserRegisterDTO userRegisterDTO) {
        String username = userRegisterDTO.getUsername();
        String password = userRegisterDTO.getPassword();

        /* 前置条件失败 */
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
            throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "用户名或密码不能为空");

        User user = new User();
        user.setUuid(getUserUuid());
        user.setUsername(username);
        user.setPassword(getSecretPassword(password));
        int countByScope = userMapper.countByScope(user);

        if (countByScope == 0) {
            int insertSelectiveRow = userMapper.insertSelective(user);

            if (insertSelectiveRow <= 0)
                throw new ServiceException("注册失败");
            else
                return insertSelectiveRow;
        } else
            throw new ServiceException(HttpStatus.SC_CREATED, "用户已存在");
    }

    /*
    * 雪花生成唯一ID
    * */
    private String getUserUuid() {
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);
        return StringUtils.substring(String.valueOf(snowflakeIdWorker.nextId()), 4);
    }

    /*
    * 获取md5加盐密码
    * */
    private String getSecretPassword(String str1) {
        String str2 = str1 + UserServiceImpl.PASSWORD_SECRET;
        return DigestUtils.md5DigestAsHex(str2.getBytes(StandardCharsets.UTF_8));
    }

}
