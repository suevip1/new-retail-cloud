package com.zhihao.newretail.pay.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Configuration
@MapperScan(basePackages = "com.zhihao.newretail.pay.dao")
public class MapperScanConfig {
}
