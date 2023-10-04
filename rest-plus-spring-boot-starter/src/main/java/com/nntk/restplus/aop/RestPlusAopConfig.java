package com.nntk.restplus.aop;

import com.nntk.restplus.abs.AbsHttpFactory;
import com.nntk.restplus.abs.AbsBasicRespObserver;
import com.nntk.restplus.entity.RestPlusResponse;
import com.nntk.restplus.abs.AbsBodyHandleRule;
import com.nntk.restplus.annotation.FormData;
import com.nntk.restplus.annotation.RestPlus;
import com.nntk.restplus.returntype.Call;
import com.nntk.restplus.returntype.RestPlusVoid;
import com.nntk.restplus.strategy.HttpRequestBaseHandler;
import com.nntk.restplus.strategy.HttpRequestSelector;
import com.nntk.restplus.util.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;


@Component
@Aspect
public class RestPlusAopConfig {

    private static final Logger log = LoggerFactory.getLogger(RestPlusAopConfig.class);

    @Resource
    private HttpRequestSelector httpRequestSelector;

    @Pointcut("execution(@com.nntk.restplus.annotation.* * *(..))")
    public void execute() {


    }

    @Around("execute()")
    public Object interceptAnnotation(ProceedingJoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();


        Class<?> clazz = method.getDeclaringClass();


        // 获取定义的结果判断逻辑和处理逻辑
        Class<AbsBodyHandleRule> respHandlerClass = AnnotationUtil.getAnnotationValue(clazz, RestPlus.class, "respHandler");
        Class<AbsBasicRespObserver> observerClass = AnnotationUtil.getAnnotationValue(clazz, RestPlus.class, "observe");


        AbsBodyHandleRule handler = SpringUtil.getBean(respHandlerClass);
        AbsBasicRespObserver observer = SpringUtil.getBean(observerClass);


        Annotation requestTypeAnnotation = Arrays.stream(method.getAnnotations()).filter(annotationValue -> httpRequestSelector.isRequestType(annotationValue.annotationType())).findAny().get();

        HttpRequestBaseHandler select = httpRequestSelector.select(requestTypeAnnotation.annotationType());


        // 获取http 工厂类
        Class<AbsHttpFactory> httpFactoryClass = AnnotationUtil.getObject(clazz, RestPlus.class, "httpFactory");
        AbsHttpFactory httpFactory = SpringUtil.getBean(httpFactoryClass);

        boolean isFormData = Arrays.stream(method.getAnnotations()).anyMatch(annotation -> annotation.annotationType() == FormData.class);

        if (isFormData) {
            httpFactory.setContentType(MediaType.MULTIPART_FORM_DATA_VALUE);
        } else {
            httpFactory.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
        if (httpFactoryClass == AbsHttpFactory.class) {
            throw new RuntimeException("you must extends AbsHttpFactory...");
        }


        // 获取返回类型
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType == Void.class) {
            RestPlusVoid vo = new RestPlusVoid();
            try {
                RestPlusResponse response = select.execute(joinPoint, httpFactory);
                vo.setHttpStatus(response.getHttpStatus());
                handler.setHttpBody(response.getBody());
            } catch (Exception e) {
                vo.setThrowable(e);
            } finally {
                vo.setRespBodyHandleRule(handler);

            }
            // 自动触发观察
            HttpRespObserver.observe(observer, vo.getThrowable(), vo.getHttpStatus(), vo.getRespBodyHandleRule());

            return vo;
        } else {
            Call<Object> call = new Call<>();
            try {
                RestPlusResponse response = select.execute(joinPoint, httpFactory);
                Type type = method.getGenericReturnType();
                Type typeArgument = TypeUtil.toParameterizedType(type).getActualTypeArguments()[0];
                call.setReturnType(typeArgument);
                call.setHttpStatus(response.getHttpStatus());
                handler.setHttpBody(response.getBody());
            } catch (Exception e) {
                call.setThrowable(e);
            } finally {
                call.setRespBodyHandleRule(handler);
                call.setConfigObserver(observer);
                call.setHttpFactory(httpFactory);
            }

            return call;
        }


    }


}
