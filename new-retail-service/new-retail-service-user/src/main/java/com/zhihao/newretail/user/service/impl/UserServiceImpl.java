package com.zhihao.newretail.user.service.impl;

import com.zhihao.newretail.api.file.feign.FileUploadFeignService;
import com.zhihao.newretail.api.user.vo.UserApiVO;
import com.zhihao.newretail.api.user.vo.UserInfoApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.MyMD5SecretUtil;
import com.zhihao.newretail.core.util.SnowflakeIdWorker;
import com.zhihao.newretail.file.consts.FileUploadDirConst;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private FileUploadFeignService fileUploadFeignService;

    /*
     * 新增用户
     * 基于用户名、密码注册
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertUser(UserRegisterDTO userRegisterDTO) {
        String username = userRegisterDTO.getUsername();
        String password = userRegisterDTO.getPassword();

        /* 前置条件失败 */
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "用户名或密码不能为空");
        }

        String uuid = getUserUUID();    // 获取分布式唯一id
        String secretPassword = MyMD5SecretUtil.getSecretPassword(password, uuid);  // 密码md5盐值加密

        User user = new User();
        user.setUuid(uuid);
        user.setUsername(username);
        user.setPassword(secretPassword);
        int countByScope = userMapper.countByScope(user);

        if (countByScope == 0) {
            int insertUserRow = userMapper.insertSelective(user);

            if (insertUserRow <= 0) {
                throw new ServiceException("保存用户失败");
            } else {
                Integer userId = user.getId();          // 获取返回主键值
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(userId);
                userInfo.setNickName("用户:" + uuid);     // 用户昵称
                userInfo.setPhoto("photoURL");          // TODO 用户头像URL
                int insertUserInfoRow = userInfoMapper.insertSelective(userInfo);

                if (insertUserInfoRow <= 0) {
                    throw new ServiceException("保存用户信息失败");
                }
                return userId;
            }
        }
        throw new ServiceException(HttpStatus.SC_CREATED, "用户已存在");
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

        if (!ObjectUtils.isEmpty(userInfo)) {
            UserInfoVO userInfoVO = new UserInfoVO();
            BeanUtils.copyProperties(userInfo, userInfoVO);
            return userInfoVO;
        }
        throw new ServiceException(HttpStatus.SC_NOT_FOUND, "用户不存在");
    }

    @Override
    public UserInfoVO updateUserInfo(Integer userId, MultipartFile file) throws IOException {
        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        String url = fileUploadFeignService.getFileUrl(file, FileUploadDirConst.USER_PHOTO);
        userInfo.setPhoto(url);
        int updateRow = userInfoMapper.updateByPrimaryKeySelective(userInfo);
        if (updateRow <= 0) {
            throw new ServiceException("修改头像失败");
        }
        return userInfo2UserInfoVO(userInfo);
    }

    /*
    * 雪花生成唯一ID
    * */
    private String getUserUUID() {
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);
        return StringUtils.substring(String.valueOf(snowflakeIdWorker.nextId()), 4);
    }

    private UserInfoVO userInfo2UserInfoVO(UserInfo userInfo) {
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userInfo, userInfoVO);
        return userInfoVO;
    }

}
