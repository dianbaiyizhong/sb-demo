package com.nntk.sb.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.nntk.restplus.AbsHttpFactory;
import com.nntk.restplus.RestPlusResponse;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * 工厂模式，实现子产品，底层映射调用
 * 1.自定义数据转化逻辑（可以用gson，fastjson，jackson等）
 * 2.自定义http请求方式，有hutool，okhttp，restTemplate
 */
@Component
public class HutoolHttpFactory extends AbsHttpFactory {
    @Override
    public String toJsonString(Object object) {
        return JSON.toJSONString(object);
    }

    @Override
    public <T> T parseObject(String json, Type tClass) {
        return JSON.parseObject(json, tClass);
    }

    @Override
    public RestPlusResponse post(String url, Map<String, String> header, Map<String, Object> bodyMap) {
        HttpRequest httpRequest = HttpUtil.createPost(url);
        httpRequest.body(JSON.toJSONString(bodyMap));
        if (header != null) {
            httpRequest.addHeaders(header);
        }
        HttpResponse response = httpRequest.execute();
        RestPlusResponse restPlusResponse = new RestPlusResponse();
        restPlusResponse.setHttpStatus(response.getStatus());
        restPlusResponse.setBody(response.body());
        return restPlusResponse;
    }

    @Override
    public RestPlusResponse put(String url, Map<String, String> headerMap, Map<String, Object> body) {
        return null;
    }

    @Override
    public RestPlusResponse delete(String url, Map<String, String> headerMap, Map<String, Object> body) {
        return null;
    }

    @Override
    public RestPlusResponse get(String url, Map<String, String> header) {
        return null;
    }
}
