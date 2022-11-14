package com.zhihao.newretail.pay.enums;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public enum OrderStatusEnum {

    NOT_PAY(0, "未支付"),
    PAY_SUCCEED(1, "支付成功"),
    NOT_TAKE(2, "待收货"),
    TAKE_SUCCEED(3, "收货成功");

    Integer code;
    String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
