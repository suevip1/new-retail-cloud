package com.zhihao.newretail.admin.aspect;

import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.admin.annotation.RequiresPermission;
import com.zhihao.newretail.admin.consts.AuthorizationConst;
import com.zhihao.newretail.admin.context.SysUserTokenContext;
import com.zhihao.newretail.admin.pojo.SysUser;
import com.zhihao.newretail.admin.service.SysUserService;
import com.zhihao.newretail.redis.util.MyRedisUtil;
import com.zhihao.newretail.security.vo.SysUserLoginVO;
import org.apache.http.HttpStatus;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequiresPermissionAspect {

    @Autowired
    private MyRedisUtil redisUtil;

    @Autowired
    private SysUserService sysUserService;

    public RequiresPermissionAspect() {

    }

    @Before("@annotation(permission)")
    public void before(RequiresPermission permission) {
        String userToken = SysUserTokenContext.getUserToken();
        SysUserLoginVO sysUserLoginVO = (SysUserLoginVO) redisUtil.getObject(userToken);
        SysUser sysUser = sysUserService.getSysUser(sysUserLoginVO.getId());
        SysUserTokenContext.clean();
        String userScope = sysUser.getSysRole().getKey();
        String scope = permission.scope();
        handleScope(scope, userScope);
    }

    private void handleScope(String scope, String userScope) {
        if (AuthorizationConst.ROOT.equals(scope) && !AuthorizationConst.ROOT.equals(userScope)) {
            throw new ServiceException(HttpStatus.SC_FORBIDDEN, "您不具备该权限");
        }
        if (AuthorizationConst.ADMIN.equals(scope)
                && !AuthorizationConst.ROOT.equals(userScope)
                && !AuthorizationConst.ADMIN.equals(userScope)) {
            throw new ServiceException(HttpStatus.SC_FORBIDDEN, "您不具备该权限");
        }
        if (AuthorizationConst.COMMON.equals(scope)
                && !AuthorizationConst.ROOT.equals(userScope)
                && !AuthorizationConst.ADMIN.equals(userScope)
                && !AuthorizationConst.COMMON.equals(userScope)) {
            throw new ServiceException(HttpStatus.SC_FORBIDDEN, "您不具备该权限");
        }
    }

}
