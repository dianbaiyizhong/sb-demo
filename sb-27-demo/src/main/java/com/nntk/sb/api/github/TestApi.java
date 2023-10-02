package com.nntk.sb.api.github;

import com.nntk.sb.rest.BasicCall;
import com.nntk.sb.rest.annotation.Body;
import com.nntk.sb.rest.annotation.JalorRestProxy;
import com.nntk.sb.rest.annotation.POST;

import java.util.Map;

@JalorRestProxy(baseUrl = "http://localhost:8080", respHandler = GithubRespBodyHandler.class)
public interface TestApi {


    @POST(url = "/login")
    BasicCall<GithubPostBodyEntity> login(@Body Map<String, Object> map);

}
