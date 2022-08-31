package com.zhihao.newretail.product.enums;

public enum ProductCacheKeyEnum {

    HOME_PRODUCT_LIST("home_product_list"),
    HOME_CATEGORY_PRODUCT_LIST("home_category_product_list");

    String key;

    public String getKey() {
        return key;
    }

    ProductCacheKeyEnum(String key) {
        this.key = key;
    }

}
