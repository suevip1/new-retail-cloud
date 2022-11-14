package com.zhihao.newretail.product.enums;

public enum CategoryEnum {

    ROOT_NODE(0, "未删除");

    Integer code;

    String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    CategoryEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
