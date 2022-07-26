package com.zhihao.newretail.product.enums;

public enum ProductEnum {

    NOT_SALEABLE(1, "无效"),

    SALEABLE(1, "有效");

    Integer code;

    String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    ProductEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
