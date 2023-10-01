package com.nntk.sb.rest;

import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Type;

@Getter
@Setter
public class BasicCall<T> {

    private int httpStatus;
    private Throwable throwable;
    private BasicRespObserver observer;
    private BasicRespBodyHandler basicRespBodyHandler;
    private Type retureType;

    public BasicCall<T> observe(BasicRespObserver observer) {
        observer.complete();
        if (throwable != null) {
            observer.callUnknownException(throwable);
            return this;
        }
        if (httpStatus != HttpStatus.OK.value()) {
            observer.callHttpFail(httpStatus, basicRespBodyHandler.httpBody);
            return this;
        }

        observer.callHttpSuccess();

        if (basicRespBodyHandler.isBusinessSuccess()) {
            observer.callBusinessSuccess();
        } else {
            observer.callBusinessFail(basicRespBodyHandler.getCode(), basicRespBodyHandler.getMessage());
        }
        return this;
    }

    public T getResult() {
        String data = basicRespBodyHandler.getData();
        return JSONObject.parseObject(data, retureType);
    }
}
