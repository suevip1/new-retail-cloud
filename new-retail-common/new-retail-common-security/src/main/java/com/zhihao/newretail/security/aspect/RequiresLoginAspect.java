package com.zhihao.newretail.security.aspect;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.security.UserLoginContext;
import com.zhihao.newretail.security.util.JwtUtil;
import com.zhihao.newretail.security.vo.UserLoginVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class RequiresLoginAspect {

    public RequiresLoginAspect() {

    }

    @Pointcut("@annotation(com.zhihao.newretail.security.annotation.RequiresLogin)")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void before() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader("token");

        if (StringUtils.isEmpty(token))
            throw new ServiceException(HttpStatus.SC_UNAUTHORIZED, "请登录后再操作");

        try {
            JwtUtil.verifierToken(token);
            UserLoginVO userLoginVO = new UserLoginVO();
            userLoginVO.setUserId(JwtUtil.getUserId(token));
            userLoginVO.setUuid(JwtUtil.getUuid(token));
            UserLoginContext.setUserLoginInfo(userLoginVO);
        } catch (TokenExpiredException e) {
            throw new ServiceException(HttpStatus.SC_UNAUTHORIZED, "令牌已失效");
        }
    }

}
