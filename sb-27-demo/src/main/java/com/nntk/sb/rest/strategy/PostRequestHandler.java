package com.nntk.sb.rest.strategy;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.nntk.sb.rest.annotation.Body;
import com.nntk.sb.rest.annotation.JalorRestProxy;
import com.nntk.sb.rest.annotation.POST;
import com.nntk.sb.rest.annotation.Path;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

@Component
public class PostRequestHandler implements HttpRequestBaseHandler {
    @Override
    public HttpResponse execute(ProceedingJoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Class clazz = method.getDeclaringClass();
        // 解析base url
        String baseUrl = AnnotationUtil.getAnnotationValue(clazz, JalorRestProxy.class, "baseUrl");
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

        String requestBody = null;

        for (Parameter parameter : parameters) {
            Object value = AnnotationUtil.getAnnotation(parameter, Body.class);
            if (value != null) {
                requestBody = JSON.toJSONString(paramMap.get(parameter.getName()));
                break;
            }
        }


        String formatUrl = StrFormatter.format(url, pathMap, false);

        HttpRequest request = HttpUtil.createPost(formatUrl);
        request.contentType(ContentType.JSON.getValue());
        request.body(requestBody);
        HttpResponse response = request.execute();
        return response;

    }


}