package com.nntk.sb.restplus.aop;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.TypeUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.ContentType;
import com.nntk.sb.restplus.AbsHttpFactory;
import com.nntk.sb.restplus.BasicRespObserver;
import com.nntk.sb.restplus.HttpPlusResponse;
import com.nntk.sb.restplus.RespBodyHandleRule;
import com.nntk.sb.restplus.annotation.FormData;
import com.nntk.sb.restplus.annotation.RestPlus;
import com.nntk.sb.restplus.returntype.Call;
import com.nntk.sb.restplus.returntype.Void;
import com.nntk.sb.restplus.strategy.HttpRequestBaseHandler;
import com.nntk.sb.restplus.strategy.HttpRequestSelector;
import com.nntk.sb.restplus.util.HttpRespObserver;
import com.nntk.sb.restplus.util.RestAnnotationUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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



        Annotation requestTypeAnnotation = Arrays.stream(method.getAnnotations()).filter(annotationValue -> httpRequestSelector.isRequestType(annotationValue.annotationType())).findAny().get();

        HttpRequestBaseHandler select = httpRequestSelector.select(requestTypeAnnotation.annotationType());


        // 获取http 工厂类
        Class<AbsHttpFactory> httpFactoryClass = RestAnnotationUtil.getObject(clazz, RestPlus.class, "httpFactory");
        AbsHttpFactory httpFactory = SpringUtil.getBean(httpFactoryClass);

        boolean isFormData = Arrays.stream(method.getAnnotations()).anyMatch(annotation -> annotation.annotationType() == FormData.class);

        if (isFormData) {
            httpFactory.setContentType(ContentType.MULTIPART.getValue());
        } else {
            httpFactory.setContentType(ContentType.JSON.getValue());
        }
        if (httpFactoryClass == AbsHttpFactory.class) {
            throw new RuntimeException("you must extends AbsHttpFactory...");
        }


        // 获取返回类型
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType == Void.class) {
            Void vo = new Void();
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
                Type typeArgument = TypeUtil.getTypeArgument(TypeUtil.getReturnType(method));
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
