package com.zhihao.newretail.rbac.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.zhihao.newretail.core.util.GsonUtil;
import com.zhihao.newretail.core.util.R;
import org.apache.http.HttpStatus;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class RbacSentinelConfig implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        R error = R.error(HttpStatus.SC_SERVICE_UNAVAILABLE, "当前访问人数过多，请稍后重试");
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(GsonUtil.obj2Json(error));
    }

}
