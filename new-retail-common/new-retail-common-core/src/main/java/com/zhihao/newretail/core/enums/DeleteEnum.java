package com.zhihao.newretail.core.enums;

public enum DeleteEnum {

    NOT_DELETE(0, "未删除"),

    DELETE(1, "已删除");

    Integer code;

    String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    DeleteEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
