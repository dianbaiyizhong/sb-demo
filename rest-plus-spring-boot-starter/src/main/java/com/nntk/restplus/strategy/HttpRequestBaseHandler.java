package com.nntk.restplus.strategy;


import com.nntk.restplus.abs.AbsHttpFactory;
import com.nntk.restplus.entity.RestPlusResponse;
import com.nntk.restplus.annotation.*;
import com.nntk.restplus.intercept.RestPlusHandleIntercept;
import com.nntk.restplus.util.AnnotationUtil;
import com.nntk.restplus.util.GenericBuilder;
import com.nntk.restplus.util.SpringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class HttpRequestBaseHandler {

    public void setRequestType(Class<? extends Annotation> requestType) {
        this.requestType = requestType;
    }

    private Class<? extends Annotation>  requestType;

    public RestPlusResponse execute(ProceedingJoinPoint joinPoint, AbsHttpFactory httpFactory) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Class<?> clazz = method.getDeclaringClass();
        // 解析base url
        String baseUrl = AnnotationUtil.getValue(clazz, RestPlus.class, "baseUrl");

        String childUrl = AnnotationUtil.getAnnotationValue(method, requestType, "url");
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
            String value = AnnotationUtil.getAnnotationValue(parameter, Path.class, "value");
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


            boolean body = AnnotationUtil.hasAnnotation(parameter, Body.class);
            boolean header = AnnotationUtil.hasAnnotation(parameter, Header.class);

            if (body) {
                requestBody = (Map<String, Object>) paramMap.get(parameter.getName());
            }
            if (header) {
                headerMap = (Map<String, String>) paramMap.get(parameter.getName());
            }
        }


        String formatUrl = parseTemplate(url, pathMap);

        HttpExecuteContext context = GenericBuilder.of(HttpExecuteContext::new)
                .with(HttpExecuteContext::setUrl,formatUrl)
                .with(HttpExecuteContext::setBodyMap,requestBody)
                .with(HttpExecuteContext::setHeaderMap,headerMap)
                .with(HttpExecuteContext::setHttpFactory,httpFactory)
                .build();
        // 拦截器模式
        Class<? extends RestPlusHandleIntercept>[] interceptList = AnnotationUtil.getObject(clazz, Intercept.class, "classType");

        for (Class<? extends RestPlusHandleIntercept> object : interceptList) {
            RestPlusHandleIntercept handleIntercept = SpringUtil.getBean(object);
            context = handleIntercept.handle(context);
        }

        // 模板方法模式
        return executeHttp(context);
    }


    public abstract RestPlusResponse executeHttp(HttpExecuteContext context);



    public static String parseTemplate(String template, Map properties) {
        if (template == null || template.isEmpty() || properties == null) {
            return template;
        }
        String r = "\\{([^\\}]+)\\}";
        Pattern pattern = Pattern.compile(r);
        Matcher matcher = pattern.matcher(template);
        while (matcher.find()) {
            String group = matcher.group();
            Object o = properties.get(group.replaceAll(r, "$1"));
            if (o != null) {
                template = template.replace(group, String.valueOf(o));
            } else {
                template = template.replace(group, "");
            }
        }
        return template;
    }

}
