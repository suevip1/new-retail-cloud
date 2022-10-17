package com.zhihao.newretail.auth.service.impl;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.zhihao.newretail.auth.enums.TokenTypeEnum;
import com.zhihao.newretail.auth.service.TokenService;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.redis.util.MyRedisUtil;
import com.zhihao.newretail.security.util.JwtUtil;
import com.zhihao.newretail.security.vo.SysUserLoginVO;
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

    /* 用户信息缓存15天 */
    private final static long CACHE_EXPIRE_TIMEOUT = 3600 * 24 * 15;

    /* 系统用户信息缓存1天 */
    private final static long SYS_CACHE_EXPIRE_TIMEOUT = 3600 * 24;

    @Override
    public String getToken(UserLoginVO userLoginVO) {
        if (!ObjectUtils.isEmpty(userLoginVO)) {
            Integer userId = userLoginVO.getUserId();
            String uuid = userLoginVO.getUuid();
            cacheUserInfo(userId, userLoginVO);
            return JwtUtil.createToken(userId, uuid);
        }
        throw new ServiceException("用户登录信息不能为空");
    }

    @Override
    public String getToken(SysUserLoginVO sysUserLoginVO) {
        if (!ObjectUtils.isEmpty(sysUserLoginVO)) {
            cacheSysUserInfo(sysUserLoginVO.getUserToken(), sysUserLoginVO);
            return JwtUtil.createToken(sysUserLoginVO.getUserToken());
        }
        throw new ServiceException("用户登录信息不能为空");
    }

    @Override
    public String verifierToken(String token, TokenTypeEnum tokenTypeEnum) {
        if (StringUtils.isEmpty(token)) { return token; }   // 未登录直接返回
        /*
        * 验证 token 类型
        * */
        if (TokenTypeEnum.SYS_USER.equals(tokenTypeEnum)) {
            return sysUserToken(token);
        } else {
            return consumerUserToken(token);
        }
    }

    /*
    * 验证消费者用户token
    * */
    private String consumerUserToken(String token) {
        Integer userId = JwtUtil.getUserId(token);
        String uuid = JwtUtil.getUuid(token);
        try {
            JwtUtil.verifierToken(token);       // 验证token有效性
            if (redisUtil.isExist(String.valueOf(userId))) {
                return token;      // token 有效、缓存用户信息有效
            } else {
                /*
                 * token 有效、缓存用户信息失效
                 * 证明信息缓存30天已到期，需要重新登录、清除token
                 * */
                // throw new ServiceException(HttpStatus.SC_UNAUTHORIZED, "凭证已过期，请重新登录");
                return null;
            }
        } catch (TokenExpiredException e) {
            /* token 失效，重新生成 token */
            if (redisUtil.isExist(String.valueOf(userId))) {
                return JwtUtil.createToken(userId, uuid);
            } else {
                // throw new ServiceException(HttpStatus.SC_UNAUTHORIZED, "凭证已过期，请重新登录");
                return null;
            }
        }
    }

    /*
    * 验证后台系统用户token
    * */
    private String sysUserToken(String token) {
        String userToken = JwtUtil.getUserToken(token);
        try {
            /* token 有效，直接返回 */
            JwtUtil.verifierToken(token);
            if (redisUtil.isExist(userToken)) {
                return token;
            } else {
                // throw new ServiceException(HttpStatus.SC_UNAUTHORIZED, "凭证已过期，请重新登录");
                return null;
            }
        } catch (TokenExpiredException e) {
            /* token 失效，重新生成 token */
            if (redisUtil.isExist(userToken)) {
                return JwtUtil.createToken(userToken);
            } else {
                // throw new ServiceException(HttpStatus.SC_UNAUTHORIZED, "凭证已过期，请重新登录");
                return null;
            }
        }
    }

    /*
    * 缓存登录用户的信息
    * */
    private void cacheUserInfo(Integer userId, UserLoginVO userLoginVO) {
        redisUtil.setObject(String.valueOf(userId), userLoginVO, CACHE_EXPIRE_TIMEOUT);
    }

    /*
    * 缓存后台系统用户信息
    * */
    private void cacheSysUserInfo(String redisKey, SysUserLoginVO sysUserLoginVO) {
        redisUtil.setObject(redisKey, sysUserLoginVO, SYS_CACHE_EXPIRE_TIMEOUT);
    }

}
