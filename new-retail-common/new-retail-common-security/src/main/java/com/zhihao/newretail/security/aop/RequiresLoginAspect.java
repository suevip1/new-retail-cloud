package com.zhihao.newretail.security.aop;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.security.context.UserLoginContext;
import com.zhihao.newretail.security.util.JwtUtil;
import com.zhihao.newretail.security.vo.SysUserLoginVO;
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
        if (StringUtils.isEmpty(token)) {
            throw new ServiceException(HttpStatus.SC_UNAUTHORIZED, "请登录后再操作");
        }
        try {
            JwtUtil.verifierToken(token);
            String userToken = JwtUtil.getUserToken(token);     // 只有后台系统的用户存在user token
            if (StringUtils.isEmpty(userToken)) {
                UserLoginVO userLoginVO = new UserLoginVO();
                userLoginVO.setUserId(JwtUtil.getUserId(token));
                userLoginVO.setUuid(JwtUtil.getUuid(token));
                UserLoginContext.setUserLoginInfo(userLoginVO);
            } else {
                SysUserLoginVO sysUserLoginVO = new SysUserLoginVO();
                sysUserLoginVO.setUserToken(userToken);
                UserLoginContext.setSysUserLoginVO(sysUserLoginVO);
            }
        } catch (TokenExpiredException e) {
            throw new ServiceException("长时间未操作，请返回首页刷新页面");
        }
    }

}
