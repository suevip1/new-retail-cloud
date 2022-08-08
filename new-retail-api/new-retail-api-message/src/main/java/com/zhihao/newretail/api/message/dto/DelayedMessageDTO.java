package com.zhihao.newretail.api.message.dto;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public class DelayedMessageDTO {

    private String content;

    private String exchange;

    private String routingKey;

    private Integer delayedTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public Integer getDelayedTime() {
        return delayedTime;
    }

    public void setDelayedTime(Integer delayedTime) {
        this.delayedTime = delayedTime;
    }

}
