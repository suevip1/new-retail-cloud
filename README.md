# new-retail-cloud

## 项目介绍
new-retail-cloud 是一套微服务商城系统，采用Spring Cloud & Alibaba、Spring Boot、MyBatis、Redis、RabbitMQ、ShardingSphere-JDBC、分布式锁、Canal等核心技术，各服务集成了注册中心、配置中心、Sentinel、网关等功能。系统属于个人开源项目，目前基础架构已得到完善（能够运行），后续更新常见高并发场景，例如：秒杀、ElasticSearch全文检索、ELK分布式日志收集等，开发本项目的初衷是能够和大家共同交流、学习、精进。

## 项目规范
本人代码洁癖，编码格式严格按照《Google Java编程风格指南》《阿里巴巴Java开发手册》要求编写（会Java后端的看懂我写的代码应该不难）。

## 目录结构
```
new-retail-cloud
├─new-retail-cloud-admin -- 系统后台服务
├─new-retail-cloud-api -- 内网接口
│  ├─new-retail-cloud-api-admin  -- 后台对内接口
│  ├─new-retail-cloud-api-file  -- 文件上传对内接口
│  ├─new-retail-cloud-api-order  -- 订单对内接口
│  ├─new-retail-cloud-api-pay  -- 支付对内接口
│  ├─new-retail-cloud-api-product  -- 商品对内接口
│  └─new-retail-cloud-api-user  -- 用户对内接口
├─new-retail-cloud-auth  -- 授权校验服务
├─new-retail-cloud-common -- 公共方法
│  ├─new-retail-cloud-core  -- 公共核心依赖
│  ├─new-retail-cloud-file  -- 文件上传相关公共代码
│  ├─new-retail-cloud-rabbitmq  -- rabbitmq相关公共代码
│  ├─new-retail-cloud-redis  -- redis相关公共代码
│  └─new-retail-cloud-security  -- 安全相关公共代码
├─new-retail-cloud-file  -- 文件上传服务
├─new-retail-cloud-gateway  -- 网关
├─new-retail-cloud-order  -- 订单服务
├─new-retail-cloud-pay  -- 支付服务
├─new-retail-cloud-product  -- 商品服务
└─new-retail-cloud-user  -- 用户服务
```

## 系统架构
架构图、技术选型表格、接口文档有时间再做吧……
