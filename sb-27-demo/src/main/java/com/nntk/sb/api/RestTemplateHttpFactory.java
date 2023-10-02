package com.nntk.sb.api;

import com.alibaba.fastjson2.JSON;
import com.nntk.sb.restplus.AbsHttpFactory;
import com.nntk.sb.restplus.HttpPlusResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.Map;

@Component
public class RestTemplateHttpFactory extends AbsHttpFactory {

    @Override
    public String toJsonString(Object object) {
        return JSON.toJSONString(object);
    }

    @Override
    public <T> T parseObject(String json, Type tClass) {
        return JSON.parseObject(json, tClass);
    }

    @Override
    public HttpPlusResponse post(String url, Map<String, String> headerMap, String body) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (headerMap != null) {
            headers.setAll(headerMap);
        }
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
        HttpPlusResponse httpPlusResponse = new HttpPlusResponse();
        httpPlusResponse.setHttpStatus(responseEntity.getStatusCodeValue());
        httpPlusResponse.setBody(responseEntity.getBody());
        return httpPlusResponse;
    }

    @Override
    public HttpPlusResponse get(String url, Map<String, String> header) {
        return null;
    }
}
