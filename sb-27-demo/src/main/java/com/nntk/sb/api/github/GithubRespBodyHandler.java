package com.nntk.sb.api.github;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.nntk.sb.http.BasicRespBodyHandler;

public class GithubRespBodyHandler<T> implements BasicRespBodyHandler<T> {

    @Override
    public boolean isBusinessSuccess(String bodyMessage) {
        JSONObject jsonObject = JSON.parseObject(bodyMessage);
        return jsonObject.getInteger("code") == 0;
    }

    @Override
    public int getCode(String bodyMessage) {
        JSONObject jsonObject = JSON.parseObject(bodyMessage);
        return jsonObject.getInteger("code");
    }

    @Override
    public String getMessage(String bodyMessage) {
        JSONObject jsonObject = JSON.parseObject(bodyMessage);
        return jsonObject.getString("message");
    }

    @Override
    public T getData(String bodyMessage) {
        return JSONObject.parseObject(JSON.parseObject(bodyMessage).getString("data"), new TypeReference<T>() {
        });
    }

}
