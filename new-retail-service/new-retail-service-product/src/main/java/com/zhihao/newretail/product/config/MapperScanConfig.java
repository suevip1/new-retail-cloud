package com.zhihao.newretail.product.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.zhihao.newretail.product.dao")
public class MapperScanConfig {
}
