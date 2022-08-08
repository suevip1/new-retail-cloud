package com.zhihao.newretail.rabbitmq.enums;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public enum MessageStatusEnum {

    NEW_MESSAGE(0, "新消息"),

    SEND_SUCCESS(1, "发送成功"),

    SEND_ERROR(-1, "发送错误");

    Integer code;

    String desc;

    MessageStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
