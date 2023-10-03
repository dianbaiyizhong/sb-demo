package com.nntk.sb.api.my;

import com.nntk.sb.restplus.annotation.*;
import com.nntk.sb.restplus.returntype.Call;
import com.nntk.sb.restplus.returntype.Void;

import java.util.List;
import java.util.Map;

@RestPlus(
        baseUrl = "http://localhost:8080",
        respHandler = MyRespBodyHandleRule.class
)
public interface MyApi extends MyBaseApi {

    @GET(url = "/list/{id}")
    Call<List<UserInfo>> getList(@Path("id") Integer userName, @QueryParam("num") int num, @QueryMap Map<String, Object> map);


    // TODO: 2023/10/2 header参数没实现
    @POST(url = "/login")
    Call<MyBodyEntity> login1(@Body Map<String, Object> map);


    @POST(url = "/login")
    Call login2(@Body Map<String, Object> map);


    @POST(url = "/login")
    Void login3(@Body Map<String, Object> map);


    @FormData
    @POST(url = "/register")
    Void register(@Body Map<String, Object> map);

    @FormData
    @POST(url = "/upload")
    Void upload(@Body Map<String, Object> map);


}
