package com.nntk.sb.rest;

import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;

@Getter
@Setter
public class Call<T> {

    private int httpStatus;
    private Throwable throwable;

    private BasicRespObserver configObserver;

    private BasicRespBodyHandler basicRespBodyHandler;
    private Type retureType;

    private boolean isObserve = false;


    public Call<T> observe(BasicRespObserver observer) {
        isObserve = true;
        HttpRespObserver.observe(observer, throwable, httpStatus, basicRespBodyHandler);
        return this;
    }

    public T executeForResult() {
        if (!isObserve) {
            observe(configObserver);
        }
        String data = basicRespBodyHandler.getData();
        return JSONObject.parseObject(data, retureType);
    }


}
