package com.zhihao.newretail.core.util;

import java.util.UUID;

public class MyUUIDUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
