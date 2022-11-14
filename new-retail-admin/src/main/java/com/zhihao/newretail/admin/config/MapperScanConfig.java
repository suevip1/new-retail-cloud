package com.zhihao.newretail.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.zhihao.newretail.admin.dao")
public class MapperScanConfig {
}
