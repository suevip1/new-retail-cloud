package com.zhihao.newretail.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "order.thread-pool")
public class ThreadPoolPramConfig {

    private int corePoolSize;   // 核心线程数

    private int maxPoolSize;    // 最大线程数

    private int workQueueSize;  // 阻塞队列数量

    private long keepAliveSeconds;  // 空闲线程存活时间(秒)

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getWorkQueueSize() {
        return workQueueSize;
    }

    public void setWorkQueueSize(int workQueueSize) {
        this.workQueueSize = workQueueSize;
    }

    public long getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(long keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    @Override
    public String toString() {
        return "ThreadPoolPramConfig{" +
                "corePoolSize=" + corePoolSize +
                ", maxPoolSize=" + maxPoolSize +
                ", workQueueSize=" + workQueueSize +
                ", keepAliveSeconds=" + keepAliveSeconds +
                '}';
    }

}
