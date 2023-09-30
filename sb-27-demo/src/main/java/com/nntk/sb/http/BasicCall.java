package com.nntk.sb.http;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BasicCall<T> {

    private T data;
    private int httpStatus;
    private Throwable throwable;
    private String responseBody;
    private BasicRespObserver observer;

    private BasicRespBodyHandler<T> basicRespBodyHandler;


    public BasicCall<T> observe(BasicRespObserver observer) {
        observer.complete();
        if (throwable != null) {
            observer.callUnknownException(throwable);
            return this;
        }
        if (httpStatus != HttpStatus.OK.value()) {
            observer.callHttpFail(httpStatus, responseBody);
            return this;
        }

        observer.callHttpSuccess();

        if (basicRespBodyHandler.isBusinessSuccess(responseBody)) {
            observer.callBusinessSuccess();
        } else {
            observer.callBusinessFail(basicRespBodyHandler.getCode(responseBody), basicRespBodyHandler.getMessage(responseBody));
        }
        data = basicRespBodyHandler.getData(responseBody);
        return this;
    }

    public T getData() {

        return basicRespBodyHandler.getData(responseBody);
    }
}
