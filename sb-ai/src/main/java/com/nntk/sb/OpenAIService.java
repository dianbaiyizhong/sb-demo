package com.nntk.sb;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OpenAIService {

    @Value("${openai.ai.key:sk-cb80f6a73a5f4ffb80b12f3260eb7217}")
    private String apiKey;

    @Value("${openai.ai.url:https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions}")
    private String apiUrl;


    public String getCompletion(Object request) {


        HttpRequest httpRequest = new HttpRequest(apiUrl);
        httpRequest.setMethod(Method.POST);
        httpRequest.header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey);
        httpRequest.body(JSON.toJSONString(request));

        HttpResponse execute = httpRequest.execute();
        System.out.println(execute.body());
        return execute.body();
    }
}
