package com.zhihao.newretail.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {

    @Bean(name = "threadPoolExecutor")
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolPramConfig threadPoolPram) {
        return new ThreadPoolExecutor(
                threadPoolPram.getCorePoolSize(),       // 核心线程数
                threadPoolPram.getMaxPoolSize(),        // 最大线程数
                threadPoolPram.getKeepAliveSeconds(),   // 空闲线程存活时间
                TimeUnit.SECONDS,                       // 单位:秒
                new LinkedBlockingQueue<>(threadPoolPram.getWorkQueueSize()),   // 阻塞(工作)队列
                Executors.defaultThreadFactory(),       // 线程工厂
                new ThreadPoolExecutor.AbortPolicy()    // 淘汰策略
        );
    }

}
