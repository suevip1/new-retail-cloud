package com.zhihao.newretail.auth.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "auth.alipay")
public class AuthAliPayParamConfig {

    private String appId;

    private String appPrivateKey;

    private String alipayPublicKey;

    private String gateway;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppPrivateKey() {
        return appPrivateKey;
    }

    public void setAppPrivateKey(String appPrivateKey) {
        this.appPrivateKey = appPrivateKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    @PostConstruct
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(gateway, appId, appPrivateKey, "json", "utf-8", alipayPublicKey, "RSA2");
    }

}
