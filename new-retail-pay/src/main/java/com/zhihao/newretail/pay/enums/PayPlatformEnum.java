package com.zhihao.newretail.pay.enums;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public enum PayPlatformEnum {

    ALIPAY_PC(1, "支付宝PC");

    Integer code;

    String desc;


    PayPlatformEnum(Integer code, String desc) {
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
