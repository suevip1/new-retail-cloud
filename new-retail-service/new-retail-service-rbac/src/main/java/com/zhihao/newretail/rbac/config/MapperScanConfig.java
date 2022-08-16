package com.zhihao.newretail.rbac.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.zhihao.newretail.rbac.dao")
public class MapperScanConfig {
}
