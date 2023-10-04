package com.nntk.restplus.util;

import com.nntk.restplus.annotation.RestPlus;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;

public class RestAnnotationUtil {


    public static <T> T getObject(Class<?> clazz, Class<? extends Annotation> annotation, String name) {

        T tClass = AnnotationUtil.getAnnotationValue(clazz, annotation, name);

        Class<?>[] is = clazz.getInterfaces();
        // 遍历继承关系，获取到对应的值
        for (Class<?> i : is) {
            T value = AnnotationUtil.getAnnotationValue(i, annotation, name);
            if (value != null) {
                tClass = value;
                break;
            }
        }
        return tClass;

    }


    public static String getValue(Class<?> clazz, Class<? extends Annotation> annotation, String name) {

        String baseUrl = AnnotationUtil.getAnnotationValue(clazz, annotation, name);
        Class<?>[] is = clazz.getInterfaces();
        // 遍历继承关系，获取到对应的值
        for (Class<?> i : is) {
            String value = AnnotationUtil.getAnnotationValue(i, RestPlus.class, name);
            if (StringUtils.hasLength(value)) {
                baseUrl = value;
                break;
            }
        }
        return baseUrl;
    }
}
