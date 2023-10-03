package com.nntk.sb.restplus.strategy;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.extra.spring.SpringUtil;
import com.nntk.sb.restplus.AbsHttpFactory;
import com.nntk.sb.restplus.HttpPlusResponse;
import com.nntk.sb.restplus.annotation.*;
import com.nntk.sb.restplus.intercept.ParamHandleIntercept;
import com.nntk.sb.restplus.util.RestAnnotationUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public abstract class HttpRequestBaseHandler {

    public HttpPlusResponse execute(ProceedingJoinPoint joinPoint, AbsHttpFactory httpFactory) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Class<?> clazz = method.getDeclaringClass();
        // 解析base url
        String baseUrl = RestAnnotationUtil.getValue(clazz, RestPlus.class, "baseUrl");

        String childUrl = AnnotationUtil.getAnnotationValue(method, POST.class, "url");
        String url = baseUrl + childUrl;

        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Object[] paramValues = joinPoint.getArgs();
        Map<String, Object> paramMap = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            String paramName = paramNames[i];
            paramMap.put(paramName, paramValues[i]);
        }

        Parameter[] parameters = method.getParameters();
        Map<String, String> pathMap = new HashMap<>();
        for (Parameter parameter : parameters) {
            String value = AnnotationUtil.getAnnotationValue(parameter, Path.class);
            if (value != null) {
                if (paramMap.containsKey(parameter.getName())) {
                    pathMap.put(value, paramMap.get(parameter.getName()).toString());
                    // 从paramMap删掉path类型参数，方便后面转换
                    paramMap.remove(parameter.getName());
                }
            }
        }

        Map<String, Object> requestBody = null;
        Map<String, String> headerMap = null;

        for (Parameter parameter : parameters) {
            Object body = AnnotationUtil.getAnnotation(parameter, Body.class);
            Object header = AnnotationUtil.getAnnotation(parameter, Header.class);

            if (body != null) {
                requestBody = (Map<String, Object>) paramMap.get(parameter.getName());
            }
            if (header != null) {
                headerMap = (Map<String, String>) paramMap.get(parameter.getName());
            }
        }


        String formatUrl = StrFormatter.format(url, pathMap, false);

        HttpExecuteContext context = HttpExecuteContext.builder()
                .url(formatUrl)
                .bodyMap(requestBody)
                .headerMap(headerMap)
                .httpFactory(httpFactory)
                .build();
        // 拦截器模式

        Class<? extends ParamHandleIntercept>[] interceptList = RestAnnotationUtil.getObject(clazz, Intercept.class, "classType");

        for (Class<? extends ParamHandleIntercept> object : interceptList) {
            ParamHandleIntercept handleIntercept = SpringUtil.getBean(object);
            context = handleIntercept.handle(context);
        }

        // 模板方法模式
        return executeHttp(context);
    }


    public abstract HttpPlusResponse executeHttp(HttpExecuteContext context);


}
