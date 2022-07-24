package com.zhihao.newretail.auth.service.impl;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.zhihao.newretail.auth.service.TokenService;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.MyUUIDUtil;
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
        if (ObjectUtils.isEmpty(userLoginVO))
            throw new ServiceException(HttpStatus.SC_PRECONDITION_FAILED, "用户登录信息不能为空");

        String userToken = MyUUIDUtil.getUUID();
        userLoginVO.setUserToken(userToken);
        cacheUserInfo(userToken, userLoginVO);
        return JwtUtil.createToken(userToken);
    }

    @Override
    public R verifierToken(String token) {
        if (StringUtils.isEmpty(token))
            return R.error(HttpStatus.SC_UNAUTHORIZED, "用户未登录").put("token", token);

        String userToken = JwtUtil.getUserToken(token);

        try {
            /* token 有效，直接返回 */
            JwtUtil.verifierToken(token);
            refreshCacheUserInfo(userToken);
            return R.ok().put("token", token);
        } catch (TokenExpiredException e) {
            e.printStackTrace();

            if (redisUtil.isExist(userToken)) {
                refreshCacheUserInfo(userToken);
                String newToken = JwtUtil.createToken(userToken);
                return R.ok().put("token", newToken);
            } else
                return R.error(HttpStatus.SC_UNAUTHORIZED, "凭证已过期，请重新登录").put("token", null);
        }
    }

    /*
    * 缓存登录用户的信息
    * */
    private void cacheUserInfo(String userToken, UserLoginVO userLoginVO) {
        redisUtil.setObject(userToken, userLoginVO, CACHE_EXPIRE_TIMEOUT);
    }

    /*
    * 刷新缓存用户数据
    * */
    private void refreshCacheUserInfo(String userToken) {
        UserLoginVO userLoginVO = (UserLoginVO) redisUtil.getObject(userToken);
        redisUtil.deleteObject(userToken);
        cacheUserInfo(userToken, userLoginVO);
    }

}
