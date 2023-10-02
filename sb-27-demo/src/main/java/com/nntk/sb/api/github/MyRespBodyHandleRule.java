package com.nntk.sb.api.github;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.nntk.sb.restplus.RespBodyHandleRule;
import org.springframework.stereotype.Component;

@Component
public class MyRespBodyHandleRule extends RespBodyHandleRule {

    private JSONObject jsonObject;

    @Override
    public void init(String httpBody) {
        this.jsonObject = JSON.parseObject(getHttpBody());
    }

    @Override
    public boolean isBusinessSuccess() {
        return getCode() == 0;
    }

    @Override
    public int getCode() {
        return jsonObject.getInteger("code");
    }

    @Override
    public String getMessage() {
        return jsonObject.getString("message");
    }

    @Override
    public String getData() {
        return jsonObject.getString("data");
    }

}
