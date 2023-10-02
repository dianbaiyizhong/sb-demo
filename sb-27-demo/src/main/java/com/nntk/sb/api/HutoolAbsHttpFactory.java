package com.nntk.sb.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.nntk.sb.restplus.AbsHttpFactory;
import com.nntk.sb.restplus.HttpPlusResponse;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Map;

@Component
public class HutoolAbsHttpFactory extends AbsHttpFactory {
    @Override
    public String toJsonString(Object object) {
        return JSON.toJSONString(object);
    }

    @Override
    public <T> T parseObject(String json, Type tClass) {
        return JSON.parseObject(json, tClass);
    }

    @Override
    public HttpPlusResponse post(String url, Map<String, String> header, String body) {
        HttpRequest httpRequest = HttpUtil.createPost(url);

        httpRequest.body(body);
        if (header != null) {
            httpRequest.addHeaders(header);
        }
        HttpResponse response = httpRequest.execute();
        HttpPlusResponse httpPlusResponse = new HttpPlusResponse();
        httpPlusResponse.setHttpStatus(response.getStatus());
        httpPlusResponse.setBody(response.body());
        return httpPlusResponse;
    }

    @Override
    public HttpPlusResponse get(String url, Map<String, String> header) {
        return null;
    }
}
