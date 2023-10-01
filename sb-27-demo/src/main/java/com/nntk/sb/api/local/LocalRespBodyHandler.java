package com.nntk.sb.api.local;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.nntk.sb.rest.BasicRespBodyHandler;

public class LocalRespBodyHandler extends BasicRespBodyHandler {


    @Override
    public boolean isBusinessSuccess() {
        JSONObject jsonObject = JSON.parseObject(httpBody);
        return jsonObject.getInteger("code") == 0;
    }

    @Override
    public int getCode() {
        JSONObject jsonObject = JSON.parseObject(httpBody);
        return jsonObject.getInteger("code");
    }

    @Override
    public String getMessage() {
        JSONObject jsonObject = JSON.parseObject(httpBody);
        return jsonObject.getString("message");
    }

    public String getData() {
        return JSONObject.parseObject(httpBody).getString("data");
    }


}
