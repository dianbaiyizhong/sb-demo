package com.nntk.restplus.util;

import cn.hutool.http.HttpStatus;
import com.nntk.restplus.RespBodyHandleRule;
import com.nntk.restplus.BasicRespObserver;

public class HttpRespObserver {


    public static void observe(BasicRespObserver observer, Throwable throwable, int httpStatus,
                               RespBodyHandleRule respBodyHandleRule) {
        respBodyHandleRule.init(respBodyHandleRule.getHttpBody());
        observer.complete();
        if (throwable != null) {
            observer.callUnknownException(throwable);
        } else {
            if (httpStatus != HttpStatus.HTTP_OK) {
                observer.callHttpFail(httpStatus, respBodyHandleRule.getHttpBody());
            } else {
                observer.callHttpSuccess();
                if (respBodyHandleRule.isBusinessSuccess()) {
                    observer.callBusinessSuccess();
                } else {
                    observer.callBusinessFail(respBodyHandleRule.getCode(), respBodyHandleRule.getMessage());
                }
            }
        }
    }

}
