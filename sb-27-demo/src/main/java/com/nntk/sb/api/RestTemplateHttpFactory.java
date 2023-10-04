package com.nntk.sb.api;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.nntk.restplus.AbsHttpFactory;
import com.nntk.restplus.HttpPlusResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
    public HttpPlusResponse post(String url, Map<String, String> headerMap, Map<String, Object> bodyMap) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(getContentType()));

        if (headerMap != null) {
            headers.setAll(headerMap);
        }
        ResponseEntity<String> responseEntity = null;

        if (getContentType().equals(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(map2MultiValueMap(bodyMap), headers);
            responseEntity = restTemplate.postForEntity(url, entity, String.class);

        } else {
            HttpEntity<String> entity = new HttpEntity<>(JSON.toJSONString(bodyMap), headers);
            responseEntity = restTemplate.postForEntity(url, entity, String.class);
        }
        HttpPlusResponse httpPlusResponse = new HttpPlusResponse();
        httpPlusResponse.setHttpStatus(responseEntity.getStatusCodeValue());
        httpPlusResponse.setBody(responseEntity.getBody());
        return httpPlusResponse;
    }

    private static MultiValueMap<String, Object> map2MultiValueMap(Map<String, Object> params) {
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        for (Map.Entry<String, Object> entry : params.entrySet())
            multiValueMap.put(entry.getKey(), Lists.newArrayList(entry.getValue()));

        return multiValueMap;
    }


    @Override
    public HttpPlusResponse put(String url, Map<String, String> headerMap,  Map<String, Object> body) {
        return null;
    }

    @Override
    public HttpPlusResponse delete(String url, Map<String, String> headerMap, Map<String, Object> body) {
        return null;
    }

    @Override
    public HttpPlusResponse get(String url, Map<String, String> header) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        HttpPlusResponse httpPlusResponse = new HttpPlusResponse();
        httpPlusResponse.setHttpStatus(responseEntity.getStatusCodeValue());
        httpPlusResponse.setBody(responseEntity.getBody());
        return httpPlusResponse;
    }
}
