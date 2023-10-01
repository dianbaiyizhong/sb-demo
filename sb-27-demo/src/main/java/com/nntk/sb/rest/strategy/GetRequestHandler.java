package com.nntk.sb.rest.strategy;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.nntk.sb.rest.annotation.GET;
import com.nntk.sb.rest.annotation.JalorRestProxy;
import com.nntk.sb.rest.annotation.Path;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Component(value = "getRequestHandler")
public class GetRequestHandler implements HttpRequestBaseHandler {
    @Override
    public HttpResponse execute(ProceedingJoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Class clazz = method.getDeclaringClass();
        // 解析base url
        String baseUrl = AnnotationUtil.getAnnotationValue(clazz, JalorRestProxy.class, "baseUrl");
        String childUrl = AnnotationUtil.getAnnotationValue(method, GET.class, "url");
        String url = baseUrl + childUrl;

        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Object[] paramValues = joinPoint.getArgs();
        Map<String, String> paramMap = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            String paramName = paramNames[i];
            if (paramValues[i] instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) paramValues[i];
                map.forEach((s, o) -> {
                    paramMap.put(s, o.toString());
                });
            } else {
                paramMap.put(paramName, paramValues[i].toString());
            }

        }

        Parameter[] parameters = method.getParameters();
        Map<String, String> pathMap = new HashMap<>();
        for (Parameter parameter : parameters) {
            String value = AnnotationUtil.getAnnotationValue(parameter, Path.class);
            if (value != null) {
                if (paramMap.containsKey(parameter.getName())) {
                    pathMap.put(value, paramMap.get(parameter.getName()));

                    // 从paramMap删掉path类型参数，方便后面转换
                    paramMap.remove(parameter.getName());
                }
            }
        }

        String formatUrl = StrFormatter.format(url, pathMap, false);

        String query = URLUtil.buildQuery(paramMap, Charset.defaultCharset());

        formatUrl = formatUrl + "?" + query;

        HttpRequest request = HttpUtil.createGet(formatUrl);

        return request.execute();

    }


}
