package com.nntk.sb.restplus.strategy;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.URLUtil;
import com.nntk.sb.restplus.AbsHttpFactory;
import com.nntk.sb.restplus.HttpPlusResponse;
import com.nntk.sb.api.HutoolAbsHttpFactory;
import com.nntk.sb.restplus.annotation.GET;
import com.nntk.sb.restplus.annotation.Path;
import com.nntk.sb.restplus.annotation.RestPlus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Component
public class GetRequestHandler implements HttpRequestBaseHandler {
    @Override
    public HttpPlusResponse execute(ProceedingJoinPoint joinPoint, AbsHttpFactory httpFactory) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Class clazz = method.getDeclaringClass();
        // 解析base url
        String baseUrl = AnnotationUtil.getAnnotationValue(clazz, RestPlus.class, "baseUrl");
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

        HutoolAbsHttpFactory factory = new HutoolAbsHttpFactory();
        HttpPlusResponse httpPlusResponse = factory.get(formatUrl, null);

        return httpPlusResponse;

    }


}
