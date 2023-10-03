package com.nntk.sb.restplus;

import lombok.Data;

import java.lang.reflect.Type;
import java.util.Map;

@Data
public abstract class AbsHttpFactory {


    private String contentType;

    public abstract String toJsonString(Object object);

    public abstract <T> T parseObject(String json, Type tClass);

    public abstract HttpPlusResponse post(String url, Map<String, String> headerMap, Map<String, Object> bodyMap);

    public abstract HttpPlusResponse put(String url, Map<String, String> headerMap, Map<String, Object> bodyMap);

    public abstract HttpPlusResponse delete(String url, Map<String, String> headerMap, Map<String, Object> bodyMap);

    public abstract HttpPlusResponse get(String url, Map<String, String> headerMap);

}
