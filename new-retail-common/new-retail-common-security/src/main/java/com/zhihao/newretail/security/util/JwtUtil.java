package com.zhihao.newretail.security.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "346db7ec5b6b43bbbde0554136be74fd";

    private static final Integer EXPIRE = 1;

    /* 创建 token */
    public static String createToken(Integer userId) {
        DateTime time = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR, EXPIRE); // 获取日期偏移量，1天后的时间
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTCreator.Builder builder = JWT.create();
        return builder.withClaim("userId", userId).withExpiresAt(time).sign(algorithm);
    }

    public static String createToken(Integer userId, String uuid) {
        DateTime time = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR, EXPIRE); // 获取日期偏移量，1天后的时间
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTCreator.Builder builder = JWT.create();
        return builder
                .withClaim("userId", userId)
                .withClaim("uuid", uuid)
                .withExpiresAt(time)
                .sign(algorithm);
    }

    public static String createToken(String userToken) {
        DateTime time = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR, EXPIRE); // 获取日期偏移量，1天后的时间
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTCreator.Builder builder = JWT.create();
        return builder.withClaim("userToken", userToken).withExpiresAt(time).sign(algorithm);
    }

    /* 解析获取 token 内容 */
    public static Integer getUserId(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("userId").asInt();
    }

    public static String getUuid(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("uuid").asString();
    }

    public static String getUserToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("userToken").asString();
    }

    /* 验证 token 是否过期 */
    public static void verifierToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        jwtVerifier.verify(token);
    }

}
