package com.zhihao.newretail.auth.service.impl;

import com.zhihao.newretail.auth.service.TokenService;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.MyUUIDUtil;
import com.zhihao.newretail.redis.util.MyRedisUtil;
import com.zhihao.newretail.security.util.JwtUtil;
import com.zhihao.newretail.security.vo.UserLoginVO;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private MyRedisUtil redisUtil;

    /* 用户信息缓存30天 */
    private final static long CACHE_EXPIRE_TIMEOUT = 3600 * 24 * 30;

    @Override
    public String getToken(UserLoginVO userLoginVO) {
        if (ObjectUtils.isEmpty(userLoginVO))
            throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "用户登录信息不能为空");

        String userToken = MyUUIDUtil.getUUID();
        userLoginVO.setUserToken(userToken);
        cacheUserInfo(userToken, userLoginVO);
        return JwtUtil.createToken(userToken);
    }

    /*
    * 缓存登录用户的信息
    * */
    private void cacheUserInfo(String userToken, UserLoginVO userLoginVO) {
        redisUtil.setObject(userToken, userLoginVO, CACHE_EXPIRE_TIMEOUT);
    }

}
