package com.zhihao.newretail.product.enums;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public enum SkuStockTypeEnum {

    SUB(1),

    UN_LOCK(2);

    Integer code;

    SkuStockTypeEnum() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    SkuStockTypeEnum(Integer code) {
        this.code = code;
    }

}
