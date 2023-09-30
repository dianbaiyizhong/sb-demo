package com.nntk.sb.http;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.nntk.sb.http.annotation.GET;
import com.nntk.sb.http.annotation.JalorRestProxy;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class JalorRestAopConfig {

    @Pointcut("execution(@com.nntk.sb.http.annotation.* * *(..))")
    public void execute() {

    }

    @Around("execute()")
    public Object interceptAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Class clazz = method.getDeclaringClass();
        Object respHandler = AnnotationUtil.getAnnotationValue(clazz, JalorRestProxy.class, "respHandler");
        BasicCall basicCall = new BasicCall();
        boolean isGet = Arrays.stream(method.getAnnotations()).allMatch(annotation -> annotation.annotationType() == GET.class);
        if (isGet) {
            try {
                HttpRequest httpRequest = HttpUtil.createGet("https://api.github.com/users/dianbaiyizhong/repos");
                HttpResponse execute = httpRequest.execute();
                basicCall.setHttpStatus(execute.getStatus());
                basicCall.setResponseBody(ResourceUtil.readStr("test.json", Charset.defaultCharset()));
                basicCall.setBasicRespBodyHandler(ReflectUtil.newInstance(((Class) respHandler).getName()));
            } catch (Exception e) {
                basicCall.setThrowable(e);
            }
        }

        return basicCall;
    }


}
