package com.zhihao.newretail.product.enums;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public enum SkuStockLockEnum {

    UN_LOCK(-1, "已解锁"),

    NOT_LOCK(0, "未锁定"),

    LOCK(1, "已锁定");

    Integer code;

    String desc;

    SkuStockLockEnum() {
    }

    SkuStockLockEnum(Integer code, String desc) {
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
