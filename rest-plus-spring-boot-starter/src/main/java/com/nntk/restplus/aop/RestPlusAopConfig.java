package com.nntk.restplus.aop;

import com.nntk.restplus.AbsHttpFactory;
import com.nntk.restplus.BasicRespObserver;
import com.nntk.restplus.HttpPlusResponse;
import com.nntk.restplus.RespBodyHandleRule;
import com.nntk.restplus.annotation.FormData;
import com.nntk.restplus.annotation.RestPlus;
import com.nntk.restplus.returntype.Call;
import com.nntk.restplus.returntype.RestPlusVoid;
import com.nntk.restplus.strategy.HttpRequestBaseHandler;
import com.nntk.restplus.strategy.HttpRequestSelector;
import com.nntk.restplus.util.*;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class RestPlusAopConfig {


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
        Class<RespBodyHandleRule> respHandlerClass = AnnotationUtil.getAnnotationValue(clazz, RestPlus.class, "respHandler");
        Class<BasicRespObserver> observerClass = AnnotationUtil.getAnnotationValue(clazz, RestPlus.class, "observe");


        RespBodyHandleRule handler = SpringContextUtil.getBean(respHandlerClass);
        BasicRespObserver observer = SpringContextUtil.getBean(observerClass);


        Annotation requestTypeAnnotation = Arrays.stream(method.getAnnotations()).filter(annotationValue -> httpRequestSelector.isRequestType(annotationValue.annotationType())).findAny().get();

        HttpRequestBaseHandler select = httpRequestSelector.select(requestTypeAnnotation.annotationType());


        // 获取http 工厂类
        Class<AbsHttpFactory> httpFactoryClass = RestAnnotationUtil.getObject(clazz, RestPlus.class, "httpFactory");
        AbsHttpFactory httpFactory = SpringContextUtil.getBean(httpFactoryClass);

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
                HttpPlusResponse response = select.execute(joinPoint, httpFactory);
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
                HttpPlusResponse response = select.execute(joinPoint, httpFactory);
                Type type = method.getGenericReturnType();
                Type typeArgument = TypeUtil.toParameterizedType(type).getActualTypeArguments()[0];
                call.setRetureType(typeArgument);
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
