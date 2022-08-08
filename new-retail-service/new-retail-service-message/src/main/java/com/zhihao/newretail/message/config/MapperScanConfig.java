package com.zhihao.newretail.message.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.zhihao.newretail.message.dao")
public class MapperScanConfig {
}
