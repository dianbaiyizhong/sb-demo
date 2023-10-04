package com.nntk.restplus;


import java.lang.reflect.Type;
import java.util.Map;

public abstract class AbsHttpFactory {

    private String contentType;
    public abstract String toJsonString(Object object);

    public abstract <T> T parseObject(String json, Type tClass);

    public abstract RestPlusResponse post(String url, Map<String, String> headerMap, Map<String, Object> bodyMap);

    public abstract RestPlusResponse put(String url, Map<String, String> headerMap, Map<String, Object> bodyMap);

    public abstract RestPlusResponse delete(String url, Map<String, String> headerMap, Map<String, Object> bodyMap);

    public abstract RestPlusResponse get(String url, Map<String, String> headerMap);


    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

}
