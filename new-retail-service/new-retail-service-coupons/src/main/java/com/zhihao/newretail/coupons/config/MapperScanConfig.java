package com.zhihao.newretail.coupons.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.zhihao.newretail.coupons.dao")
public class MapperScanConfig {
}
