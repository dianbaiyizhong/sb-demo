package com.nntk.sb.rest;

import org.springframework.http.HttpStatus;

public class HttpRespObserver {


    public static void observe(BasicRespObserver observer, Throwable throwable, int httpStatus,
                               BasicRespBodyHandler basicRespBodyHandler) {
        observer.complete();
        if (throwable != null) {
            observer.callUnknownException(throwable);
        } else {
            if (httpStatus != HttpStatus.OK.value()) {
                observer.callHttpFail(httpStatus, basicRespBodyHandler.httpBody);
            } else {
                observer.callHttpSuccess();
                if (basicRespBodyHandler.isBusinessSuccess()) {
                    observer.callBusinessSuccess();
                } else {
                    observer.callBusinessFail(basicRespBodyHandler.getCode(), basicRespBodyHandler.getMessage());
                }
            }
        }
    }

}
