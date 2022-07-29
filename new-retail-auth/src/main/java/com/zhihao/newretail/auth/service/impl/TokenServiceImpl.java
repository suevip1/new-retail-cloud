package com.zhihao.newretail.auth.service.impl;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.zhihao.newretail.auth.service.TokenService;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.redis.util.MyRedisUtil;
import com.zhihao.newretail.security.util.JwtUtil;
import com.zhihao.newretail.security.vo.UserLoginVO;
import org.apache.commons.lang3.StringUtils;
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
        if (!ObjectUtils.isEmpty(userLoginVO)) {
            Integer userId = userLoginVO.getUserId();
            String uuid = userLoginVO.getUuid();
            cacheUserInfo(userId, userLoginVO);
            return JwtUtil.createToken(userId, uuid);
        }
        throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "用户登录信息不能为空");
    }

    @Override
    public R verifierToken(String token) {
        if (StringUtils.isEmpty(token))
            return R.error(HttpStatus.SC_UNAUTHORIZED, "用户未登录").put("token", token);

        Integer userId = JwtUtil.getUserId(token);
        String uuid = JwtUtil.getUuid(token);
        try {
            /* token 有效，直接返回 */
            JwtUtil.verifierToken(token);
            refreshCacheUserInfo(userId);
            return R.ok().put("token", token);
        } catch (TokenExpiredException e) {
            if (redisUtil.isExist(String.valueOf(userId))) {
                refreshCacheUserInfo(userId);
                String newToken = JwtUtil.createToken(userId, uuid);
                return R.ok().put("token", newToken);
            } else
                return R.error(HttpStatus.SC_UNAUTHORIZED, "凭证已过期，请重新登录").put("token", null);
        }
    }

    /*
    * 缓存登录用户的信息
    * */
    private void cacheUserInfo(Integer userId, UserLoginVO userLoginVO) {
        redisUtil.setObject(String.valueOf(userId), userLoginVO, CACHE_EXPIRE_TIMEOUT);
    }

    /*
    * 刷新缓存用户数据
    * */
    private void refreshCacheUserInfo(Integer userId) {
        UserLoginVO userLoginVO = (UserLoginVO) redisUtil.getObject(String.valueOf(userId));
        redisUtil.deleteObject(String.valueOf(userId));
        cacheUserInfo(userId, userLoginVO);
    }

}
