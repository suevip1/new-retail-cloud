package com.zhihao.newretail.user.service.impl;

import com.zhihao.newretail.api.file.feign.FileUploadFeignService;
import com.zhihao.newretail.api.user.dto.UserApiDTO;
import com.zhihao.newretail.api.user.vo.UserApiVO;
import com.zhihao.newretail.api.user.vo.UserInfoApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.GsonUtil;
import com.zhihao.newretail.core.util.MyMD5SecretUtil;
import com.zhihao.newretail.core.util.SnowflakeIdWorker;
import com.zhihao.newretail.file.consts.FileUploadDirConst;
import com.zhihao.newretail.redis.util.MyRedisUtil;
import com.zhihao.newretail.user.dao.UserInfoMapper;
import com.zhihao.newretail.user.dao.UserMapper;
import com.zhihao.newretail.user.form.UserRegisterForm;
import com.zhihao.newretail.user.pojo.User;
import com.zhihao.newretail.user.pojo.UserInfo;
import com.zhihao.newretail.user.form.UpdateNickNameForm;
import com.zhihao.newretail.user.vo.UserInfoVO;
import com.zhihao.newretail.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.zhihao.newretail.user.consts.UserCacheKeyConst.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private MyRedisUtil redisUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private FileUploadFeignService fileUploadFeignService;

    private final static long CACHE_EXPIRE_TIMEOUT = 3600 * 24 * 15;    // 用户信息缓存15天

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUser(UserRegisterForm form) {
        String username = form.getUsername();
        String password = form.getPassword();
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
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(user.getId());
                userInfo.setNickName("用户" + uuid);     // 用户昵称
                userInfo.setPhoto("https://zh-product.oss-cn-shenzhen.aliyuncs.com/user-photo/photo.png");  // 用户默认头像
                int insertUserInfoRow = userInfoMapper.insertSelective(userInfo);
                if (insertUserInfoRow <= 0) {
                    throw new ServiceException("保存用户信息失败");
                }
                return insertUserRow;
            }
        }
        throw new ServiceException("用户已存在");
    }

    @Override
    public UserInfoVO getUserInfoVO(Integer userId) {
        RLock lock = redissonClient.getLock(String.format(USER_INFO_LOCK, userId));
        lock.lock();
        try {
            String redisKey = String.format(USER_INFO, userId);
            String str = (String) redisUtil.getObject(redisKey);
            if (StringUtils.isEmpty(str)) {
                UserInfo userInfo = userInfoMapper.selectByUserId(userId);
                if (ObjectUtils.isEmpty(userInfo)) {
                    redisUtil.setObject(redisKey, PRESENT, 43200L);
                    throw new ServiceException("用户信息不存在");
                } else {
                    UserInfoVO userInfoVO = userInfo2UserInfoVO(userInfo);
                    redisUtil.setObject(redisKey, GsonUtil.obj2Json(userInfoVO), CACHE_EXPIRE_TIMEOUT);
                    return userInfoVO;
                }
            }
            UserInfoVO userInfoVO = GsonUtil.json2Obj(str, UserInfoVO.class);
            if (ObjectUtils.isEmpty(userInfoVO)) {
                throw new ServiceException("用户信息不存在");
            }
            return userInfoVO;
        } finally {
            lock.unlock();
        }
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

    @Override
    public UserInfoVO updateUserInfo(Integer userId, UpdateNickNameForm form) {
        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        userInfo.setNickName(form.getNickName());
        int updateRow = userInfoMapper.updateByPrimaryKeySelective(userInfo);
        if (updateRow <= 0) {
            throw new ServiceException("修改昵称失败");
        }
        return userInfo2UserInfoVO(userInfo);
    }

    @Override
    public UserApiVO getUserApiVO(User scope) {
        User user = userMapper.selectByScope(scope);
        if (!ObjectUtils.isEmpty(user)) {
            UserInfo userInfo = userInfoMapper.selectByUserId(user.getId());
            if (!ObjectUtils.isEmpty(userInfo)) {
                return user2UserApiVO(user, userInfo);
            }
            throw new ServiceException("用户不存在");
        }
        throw new ServiceException("用户不存在");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserApiVO getUserApiVOByAliPayUserId(UserApiDTO userApiDTO) {
        User scope = userApiDTO2User(userApiDTO);
        User user = userMapper.selectByScope(scope);
        if (!ObjectUtils.isEmpty(user)) {
            return user2UserApiVO(user, user.getUserInfo());
        } else {
            insertUserByUserApiDTO(userApiDTO);
            user = userMapper.selectByScope(scope);
            return user2UserApiVO(user, user.getUserInfo());
        }
    }

    @Override
    public UserInfoApiVO getUserInfoApiVO(Integer userId) {
        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        return userInfo2UserInfoApiVO(userInfo);
    }

    @Override
    public List<UserInfoApiVO> listUserInfoApiVOS(Set<Integer> userIdSet) {
        List<UserInfo> userInfoList = userInfoMapper.selectListByUserIdSet(userIdSet);
        return userInfoList.stream().map(this::userInfo2UserInfoApiVO).collect(Collectors.toList());
    }

    /*
    * 雪花生成唯一ID
    * */
    private String getUserUUID() {
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);
        return StringUtils.substring(String.valueOf(snowflakeIdWorker.nextId()), 4);
    }

    private User userApiDTO2User(UserApiDTO userApiDTO) {
        User user = new User();
        BeanUtils.copyProperties(userApiDTO, user);
        return user;
    }

    private UserInfoVO userInfo2UserInfoVO(UserInfo userInfo) {
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userInfo, userInfoVO);
        return userInfoVO;
    }

    private UserApiVO user2UserApiVO(User user, UserInfo userInfo) {
        UserApiVO userApiVO = new UserApiVO();
        UserInfoApiVO userInfoApiVO = new UserInfoApiVO();
        BeanUtils.copyProperties(user, userApiVO);
        BeanUtils.copyProperties(userInfo, userInfoApiVO);
        userApiVO.setUserInfoApiVO(userInfoApiVO);
        return userApiVO;
    }

    private UserInfoApiVO userInfo2UserInfoApiVO(UserInfo userInfo) {
        UserInfoApiVO userInfoApiVO = new UserInfoApiVO();
        BeanUtils.copyProperties(userInfo, userInfoApiVO);
        return userInfoApiVO;
    }

    private void insertUserByUserApiDTO(UserApiDTO userApiDTO) {
        String uuid = getUserUUID();
        String weChat = userApiDTO.getWeChat();
        String photo = userApiDTO.getPhoto();
        User user = new User();
        user.setUuid(uuid);
        user.setWeChat(weChat);
        int insertUserRow = userMapper.insertSelective(user);
        if (insertUserRow > 0) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(user.getId());
            userInfo.setNickName("用户" + uuid);
            userInfo.setPhoto(photo);
            int insertUserInfoRow = userInfoMapper.insertSelective(userInfo);
            if (insertUserInfoRow <= 0) {
                throw new ServiceException("保存用户信息失败");
            }
        } else {
            throw new ServiceException("保存用户失败");
        }
    }

}
