package com.nntk.sb.rest;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.TypeUtil;
import cn.hutool.http.HttpResponse;
import com.nntk.sb.rest.annotation.GET;
import com.nntk.sb.rest.annotation.JalorRestProxy;
import com.nntk.sb.rest.annotation.LOCAL;
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
import java.nio.charset.Charset;
import java.util.Arrays;

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
        Class clazz = method.getDeclaringClass();
        Type typeArgument = TypeUtil.getTypeArgument(TypeUtil.getReturnType(method));
        BasicCall basicCall = new BasicCall<>();
        basicCall.setRetureType(typeArgument);
        Class respHandler = AnnotationUtil.getAnnotationValue(clazz, JalorRestProxy.class, "respHandler");
        HttpRequestBaseHandler select = httpRequestSelector.select(method.getAnnotations()[0].annotationType());
        try {
            HttpResponse response = select.execute(joinPoint);
            basicCall.setHttpStatus(response.getStatus());
            BasicRespBodyHandler handler = ReflectUtil.newInstance(respHandler.getName());
            handler.setHttpBody(response.body());
            basicCall.setBasicRespBodyHandler(handler);
        } catch (Exception e) {
            basicCall.setThrowable(e);
        }
        boolean isLocal = Arrays.stream(method.getAnnotations()).allMatch(annotation -> annotation.annotationType() == LOCAL.class);
        if (isLocal) {
            try {
                basicCall.setHttpStatus(200);
                basicCall.setBasicRespBodyHandler(ReflectUtil.newInstance(respHandler.getName()));
            } catch (Exception e) {
                basicCall.setThrowable(e);
            }
        }


        return basicCall;
    }


}
