//package com.zhihao.newretail.gateway.filter;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
///*
//* 全局拦截
//* 项目使用 AOP 自定义注解处理，此类作废
//* */
////@Configuration
////public class AuthorizeFilter implements GlobalFilter, Ordered {
////
////    @Override
////    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
////        return null;
////    }
////
////    @Override
////    public int getOrder() {
////        return 0;
////    }
////
////}
