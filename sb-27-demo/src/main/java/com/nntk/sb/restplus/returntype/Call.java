package com.nntk.sb.restplus.returntype;

import com.alibaba.fastjson2.JSONObject;
import com.nntk.sb.restplus.BasicRespObserver;
import com.nntk.sb.restplus.RespBodyHandleRule;
import com.nntk.sb.restplus.util.HttpRespObserver;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;

@Getter
@Setter
public class Call<T> {

    private int httpStatus;
    private Throwable throwable;

    private BasicRespObserver configObserver;

    private RespBodyHandleRule respBodyHandleRule;
    private Type retureType;

    private boolean isObserve = false;


    public Call<T> observe(BasicRespObserver observer) {
        isObserve = true;
        HttpRespObserver.observe(observer, throwable, httpStatus, respBodyHandleRule);
        return this;
    }

    public T executeForResult() {
        if (!isObserve) {
            observe(configObserver);
        }
        String data = respBodyHandleRule.getHttpBody();
        return JSONObject.parseObject(data, retureType);
    }

    public T executeForData() {
        if (!isObserve) {
            observe(configObserver);
        }
        String data = respBodyHandleRule.getData();
        return JSONObject.parseObject(data, retureType);
    }


}
