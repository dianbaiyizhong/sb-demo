package com.nntk.restplus.util;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

public class AnnotationUtil {

    @SuppressWarnings("unchecked")
    public static <T> T getAnnotationValue(AnnotatedElement annotationEle, Class<? extends Annotation> annotationType, String propertyName) {
        Annotation annotation = AnnotationUtils.getAnnotation(annotationEle, annotationType);
        if (null == annotation) {
            return null;
        } else {
            Method method = ReflectionUtils.findMethod(annotation.annotationType(), propertyName);
            return null == method ? null : (T) ReflectionUtils.invokeMethod(method, annotation, new Object[0]);
        }
    }


    public static boolean hasAnnotation(AnnotatedElement annotatedElement, Class<? extends Annotation> annotationType) {
        return AnnotationUtils.getAnnotation(annotatedElement, annotationType) != null;
    }
}
