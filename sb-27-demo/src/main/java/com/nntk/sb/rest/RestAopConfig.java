package com.nntk.sb.rest;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.TypeUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpResponse;
import com.nntk.sb.rest.annotation.RestPlus;
import com.nntk.sb.rest.strategy.HttpRequestBaseHandler;
import com.nntk.sb.rest.strategy.HttpRequestSelector;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

@Component
@Aspect
@Slf4j
public class RestAopConfig {

    @Resource
    private HttpRequestSelector httpRequestSelector;

    @Pointcut("execution(@com.nntk.sb.rest.annotation.* * *(..))")
    public void execute() {

    }

    @Around("execute()")
    public Object interceptAnnotation(ProceedingJoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();


        Type genericReturnType = method.getGenericReturnType();

        Class clazz = method.getDeclaringClass();


        // 获取定义的结果判断逻辑和处理逻辑
        Class<BasicRespBodyHandler> respHandlerClass = AnnotationUtil.getAnnotationValue(clazz, RestPlus.class, "respHandler");
        Class<BasicRespObserver> observerClass = AnnotationUtil.getAnnotationValue(clazz, RestPlus.class, "observe");
        BasicRespBodyHandler handler = SpringUtil.getBean(respHandlerClass);
        BasicRespObserver observer = SpringUtil.getBean(observerClass);

        HttpRequestBaseHandler select = httpRequestSelector.select(method.getAnnotations()[0].annotationType());

        if (genericReturnType == Void.class) {
            Void vo = new Void();
            try {
                HttpPlusResponse response = select.execute(joinPoint);
                vo.setHttpStatus(response.getHttpStatus());
                handler.setHttpBody(response.getBody());
                vo.setBasicRespBodyHandler(handler);
            } catch (Exception e) {
                vo.setThrowable(e);
            }
            vo.observe(observer);
            return vo;
        } else {
            Call call = new Call<>();
            try {
                HttpPlusResponse response = select.execute(joinPoint);
                Type typeArgument = TypeUtil.getTypeArgument(TypeUtil.getReturnType(method));
                call.setRetureType(typeArgument);
                call.setHttpStatus(response.getHttpStatus());
                handler.setHttpBody(response.getBody());
                call.setBasicRespBodyHandler(handler);
                call.setConfigObserver(observer);
            } catch (Exception e) {
                call.setThrowable(e);
            }

            return call;
        }


    }


}
