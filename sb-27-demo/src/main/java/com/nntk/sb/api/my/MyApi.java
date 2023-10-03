package com.nntk.sb.api.my;

import com.nntk.sb.restplus.annotation.*;
import com.nntk.sb.restplus.returntype.Call;

import java.util.List;
import java.util.Map;

@RestPlus(
        baseUrl = "http://localhost:8080",
        respHandler = MyRespBodyHandleRule.class
)
@Intercept(classType = {MyIntercept.class, MyIntercept2.class})
public interface MyApi extends MyBaseApi {

    @GET(url = "/list/{id}")
    Call<List<UserInfo>> getList(@Path("id") Integer userName, @QueryParam("num") int num, @QueryMap Map<String, Object> map);


    @POST(url = "/login")
    Call<MyBodyEntity> login1(@Body Map<String, Object> map);


    @POST(url = "/login")
    Call<?> login2(@Body Map<String, Object> map);


    @POST(url = "/login")
    void login3(@Body Map<String, Object> map);


    @FormData
    @POST(url = "/register")
    void register(@Body Map<String, Object> map, @Header Map<String, String> headerMap);

    @FormData
    @POST(url = "/upload")
    void upload(@Body Map<String, Object> map);


}
