package com.zhihao.newretail.core.util;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class MyMD5SecretUtil {

    /*
     * 获取md5加盐密码
     * */
    public static String getSecretPassword(String str1, String str2) {
        String str3 = str1 + str2;
        return DigestUtils.md5DigestAsHex(str3.getBytes(StandardCharsets.UTF_8));
    }

}
