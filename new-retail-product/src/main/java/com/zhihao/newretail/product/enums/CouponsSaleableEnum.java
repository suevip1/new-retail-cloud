package com.zhihao.newretail.product.enums;

/*
* 优惠券是否失效
* */
public enum CouponsSaleableEnum {

    NOT_SALEABLE(0, "失效"),

    SALEABLE(1, "有效");

    Integer code;

    String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    CouponsSaleableEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
