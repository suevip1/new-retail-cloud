package com.zhihao.newretail.order.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/*
* 处理feign请求丢失请求头问题
* */
@Configuration
public class OrderFeignConfig {

    @Bean(name = "requestInterceptor")
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (!ObjectUtils.isEmpty(requestAttributes)) {
                HttpServletRequest request = requestAttributes.getRequest();

                if (!ObjectUtils.isEmpty(request)) {
                    String token = request.getHeader("token");
                    requestTemplate.header("token", token);
                }
            }
        };
    }

}
