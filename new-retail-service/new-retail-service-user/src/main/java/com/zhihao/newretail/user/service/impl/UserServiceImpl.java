package com.zhihao.newretail.user.service.impl;

import com.zhihao.newretail.api.user.vo.UserApiVO;
import com.zhihao.newretail.api.user.vo.UserInfoApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.MyMD5SecretUtil;
import com.zhihao.newretail.core.util.SnowflakeIdWorker;
import com.zhihao.newretail.user.dao.UserInfoMapper;
import com.zhihao.newretail.user.dao.UserMapper;
import com.zhihao.newretail.user.form.UserRegisterForm;
import com.zhihao.newretail.user.pojo.User;
import com.zhihao.newretail.user.pojo.UserInfo;
import com.zhihao.newretail.user.pojo.vo.UserInfoVO;
import com.zhihao.newretail.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    /*
     * 新增用户
     * 基于用户名、密码注册
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer register(UserRegisterForm form) {
        String username = form.getUsername();
        String password = form.getPassword();
        String uid = getUserUID();    // 获取分布式唯一id
        String secretPassword = MyMD5SecretUtil.getSecretPassword(password, uid);  // 密码md5盐值加密

        User user = new User();
        user.setUuid(uid);
        user.setUsername(username);
        user.setPassword(secretPassword);
        Integer countUser = countUserByScope(user);     // 用户是否存在

        if (countUser == 0) {
            insertUser(user);                   // 保存用户
            Integer userId = user.getId();      // 插入成功返回主键
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);         // 用户id
            userInfo.setNickName("用户:" + uid); // 用户昵称
            userInfo.setPhoto("photoURL");      // TODO 用户头像URL
            insertUserInfo(userInfo);           // 保存用户信息

            return userId;
        } else {
            throw new ServiceException(HttpStatus.SC_CREATED, "用户已存在");
        }
    }

    /*
     * 获取用户基本信息
     * */
    @Override
    public UserApiVO getUserApiVO(User scope) {
        UserApiVO userApiVO = new UserApiVO();
        UserInfoApiVO userInfoApiVO = new UserInfoApiVO();
        User user = userMapper.selectByScope(scope);

        if (!ObjectUtils.isEmpty(user)) {
            UserInfo userInfo = userInfoMapper.selectByUserId(user.getId());

            if (ObjectUtils.isEmpty(userInfo)) {
                throw new ServiceException(HttpStatus.SC_NOT_FOUND, "用户不存在");
            }
            BeanUtils.copyProperties(user, userApiVO);
            BeanUtils.copyProperties(userInfo, userInfoApiVO);
            userApiVO.setUserInfoApiVO(userInfoApiVO);
            return userApiVO;
        }
        throw new ServiceException(HttpStatus.SC_NOT_FOUND, "用户不存在");
    }

    @Override
    public UserInfoVO getUserInfoVO(Integer userId) {
        UserInfo userInfo = userInfoMapper.selectByUserId(userId);

        if (ObjectUtils.isEmpty(userInfo))
            throw new ServiceException(HttpStatus.SC_NOT_FOUND, "用户不存在");

        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userInfo, userInfoVO);
        return userInfoVO;
    }

    /*
    * 雪花生成唯一ID
    * */
    private String getUserUID() {
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);
        return StringUtils.substring(String.valueOf(snowflakeIdWorker.nextId()), 4);
    }

    private Integer countUserByScope(User user) {
        return userMapper.countByScope(user);
    }

    private void insertUser(User user) {
        int insertUserRow = userMapper.insertSelective(user);

        if (insertUserRow <= 0) {
            throw new ServiceException("保存用户失败");
        }
    }

    private void insertUserInfo(UserInfo userInfo) {
        int insertUserInfoRow = userInfoMapper.insertSelective(userInfo);

        if (insertUserInfoRow <= 0) {
            throw new ServiceException("保存用户信息失败");
        }
    }

}
