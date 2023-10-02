package com.nntk.sb.api.github;

import com.nntk.sb.api.DefaultResultObserver;
import com.nntk.sb.api.HutoolAbsHttpFactory;
import com.nntk.sb.restplus.returntype.Call;
import com.nntk.sb.restplus.returntype.Void;
import com.nntk.sb.restplus.annotation.*;

import java.util.List;
import java.util.Map;

@RestPlus(
        baseUrl = "http://localhost:8080",
        respHandler = MyRespBodyHandleRule.class,
        observe = DefaultResultObserver.class,
        httpFactory = HutoolAbsHttpFactory.class
)
public interface MyApi {

    @GET(url = "/list/{id}")
    Call<List<UserInfo>> getList(@Path("id") Integer userName, @QueryParam("num") int num, @QueryMap Map<String, Object> map);


    @POST(url = "/login")
    Call<MyBodyEntity> login1(@Body Map<String, Object> map);


    @POST(url = "/login")
    Call<Object> login2(@Body Map<String, Object> map);


    @POST(url = "/login")
    Void login3(@Body Map<String, Object> map);


}
