package com.nntk.sb.api.github;

import com.nntk.sb.api.DefaultResultObserver;
import com.nntk.sb.api.HutoolHttpFactory;
import com.nntk.sb.rest.Call;
import com.nntk.sb.rest.Void;
import com.nntk.sb.rest.annotation.Body;
import com.nntk.sb.rest.annotation.POST;
import com.nntk.sb.rest.annotation.RestPlus;

import java.util.Map;

@RestPlus(
        baseUrl = "http://localhost:8080",
        respHandler = GithubRespBodyHandler.class,
        observe = DefaultResultObserver.class,
        httpFactory = HutoolHttpFactory.class
)
public interface TestApi {


    @POST(url = "/login")
    Call<GithubPostBodyEntity> login(@Body Map<String, Object> map);


    @POST(url = "/login")
    Call login2(@Body Map<String, Object> map);


    @POST(url = "/login")
    Void login3(@Body Map<String, Object> map);


}
