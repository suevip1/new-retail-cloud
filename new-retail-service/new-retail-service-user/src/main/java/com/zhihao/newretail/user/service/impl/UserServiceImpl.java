package com.zhihao.newretail.user.service.impl;

import com.zhihao.newretail.api.user.vo.UserApiVO;
import com.zhihao.newretail.api.user.vo.UserInfoApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.MyMD5SecretUtil;
import com.zhihao.newretail.core.util.SnowflakeIdWorker;
import com.zhihao.newretail.user.dao.UserInfoMapper;
import com.zhihao.newretail.user.dao.UserMapper;
import com.zhihao.newretail.user.pojo.User;
import com.zhihao.newretail.user.pojo.UserInfo;
import com.zhihao.newretail.user.pojo.dto.UserRegisterDTO;
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
    public int insertUser(UserRegisterDTO userRegisterDTO) {
        String username = userRegisterDTO.getUsername();
        String password = userRegisterDTO.getPassword();

        /* 前置条件失败 */
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
            throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "用户名或密码不能为空");

        String uuid = getUserUuid();    // 获取分布式唯一id
        String secretPassword = MyMD5SecretUtil.getSecretPassword(password, uuid);  // 密码md5盐值加密

        User user = new User();
        user.setUuid(uuid);
        user.setUsername(username);
        user.setPassword(secretPassword);
        int countByScope = userMapper.countByScope(user);

        if (countByScope == 0) {
            int insertUserRow = userMapper.insertSelective(user);

            if (insertUserRow <= 0)
                throw new ServiceException("注册失败");
            else {
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(user.getId());
                userInfo.setNickName("用户:" + uuid);
                userInfo.setPhoto("photoURL");
                int insertUserInfoRow = userInfoMapper.insertSelective(userInfo);

                if (insertUserInfoRow <= 0)
                    throw new ServiceException("注册失败");

                return insertUserRow;
            }
        } else
            throw new ServiceException(HttpStatus.SC_CREATED, "用户已存在");
    }

    /*
     * 获取用户基本信息
     * */
    @Override
    public UserApiVO getUserInfo(User scope) {
        User user = userMapper.selectByScope(scope);

        if (ObjectUtils.isEmpty(user))
            throw new ServiceException(HttpStatus.SC_NOT_FOUND, "用户不存在");

        UserInfo userInfo = userInfoMapper.selectByUserId(user.getId());

        if (ObjectUtils.isEmpty(userInfo))
            throw new ServiceException(HttpStatus.SC_NOT_FOUND, "用户不存在");

        UserApiVO userApiVO = new UserApiVO();
        UserInfoApiVO userInfoApiVO = new UserInfoApiVO();
        BeanUtils.copyProperties(user, userApiVO);
        BeanUtils.copyProperties(userInfo, userInfoApiVO);
        userApiVO.setUserInfoApiVO(userInfoApiVO);

        return userApiVO;
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
    private String getUserUuid() {
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);
        return StringUtils.substring(String.valueOf(snowflakeIdWorker.nextId()), 4);
    }

}
