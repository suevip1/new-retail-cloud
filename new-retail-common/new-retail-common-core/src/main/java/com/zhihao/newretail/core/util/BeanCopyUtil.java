package com.zhihao.newretail.core.util;

import org.springframework.beans.BeanUtils;

public class BeanCopyUtil {

    public static <T> T source2Target(Object sources, Class<T> clazz) {
        try {
            T target = clazz.newInstance();
            BeanUtils.copyProperties(sources, target);
            return target;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
