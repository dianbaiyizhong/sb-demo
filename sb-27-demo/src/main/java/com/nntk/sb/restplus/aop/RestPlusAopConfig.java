package com.nntk.sb.restplus.aop;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.TypeUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.nntk.sb.restplus.*;
import com.nntk.sb.restplus.returntype.Call;
import com.nntk.sb.restplus.returntype.Void;
import com.nntk.sb.restplus.annotation.RestPlus;
import com.nntk.sb.restplus.strategy.HttpRequestBaseHandler;
import com.nntk.sb.restplus.strategy.HttpRequestSelector;
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
public class RestPlusAopConfig {

    @Resource
    private HttpRequestSelector httpRequestSelector;

    @Pointcut("execution(@com.nntk.sb.restplus.annotation.* * *(..))")
    public void execute() {

    }

    @Around("execute()")
    public Object interceptAnnotation(ProceedingJoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();


        Class<?> clazz = method.getDeclaringClass();


        // 获取定义的结果判断逻辑和处理逻辑
        Class<RespBodyHandleRule> respHandlerClass = AnnotationUtil.getAnnotationValue(clazz, RestPlus.class, "respHandler");
        Class<BasicRespObserver> observerClass = AnnotationUtil.getAnnotationValue(clazz, RestPlus.class, "observe");
        RespBodyHandleRule handler = SpringUtil.getBean(respHandlerClass);
        BasicRespObserver observer = SpringUtil.getBean(observerClass);

        HttpRequestBaseHandler select = httpRequestSelector.select(method.getAnnotations()[0].annotationType());

        // 获取返回类型
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType == Void.class) {
            Void vo = new Void();
            try {
                HttpPlusResponse response = select.execute(joinPoint);
                vo.setHttpStatus(response.getHttpStatus());
                handler.setHttpBody(response.getBody());
                vo.setRespBodyHandleRule(handler);
            } catch (Exception e) {
                vo.setThrowable(e);
            }
            // 自动触发观察
            vo.observe(observer);
            return vo;
        } else {
            Call<Object> call = new Call<>();
            try {
                HttpPlusResponse response = select.execute(joinPoint);
                Type typeArgument = TypeUtil.getTypeArgument(TypeUtil.getReturnType(method));
                call.setRetureType(typeArgument);
                call.setHttpStatus(response.getHttpStatus());
                handler.setHttpBody(response.getBody());
                call.setRespBodyHandleRule(handler);
                call.setConfigObserver(observer);
            } catch (Exception e) {
                call.setThrowable(e);
            }

            return call;
        }


    }


}
