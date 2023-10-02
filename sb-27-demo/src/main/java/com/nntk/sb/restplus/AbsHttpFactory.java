package com.nntk.sb.restplus;

import java.lang.reflect.Type;
import java.util.Map;

public abstract class AbsHttpFactory {

    public abstract String toJsonString(Object object);

    public abstract <T> T parseObject(String json, Type tClass);

    public abstract HttpPlusResponse post(String url, Map<String, String> header, String body);

    public abstract HttpPlusResponse get(String url, Map<String, String> header);

}
