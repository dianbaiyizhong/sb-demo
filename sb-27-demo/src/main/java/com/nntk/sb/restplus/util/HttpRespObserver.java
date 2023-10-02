package com.nntk.sb.restplus.util;

import com.nntk.sb.restplus.RespBodyHandleRule;
import com.nntk.sb.restplus.BasicRespObserver;
import org.springframework.http.HttpStatus;

public class HttpRespObserver {


    public static void observe(BasicRespObserver observer, Throwable throwable, int httpStatus,
                               RespBodyHandleRule respBodyHandleRule) {
        respBodyHandleRule.init(respBodyHandleRule.getHttpBody());
        observer.complete();
        if (throwable != null) {
            observer.callUnknownException(throwable);
        } else {
            if (httpStatus != HttpStatus.OK.value()) {
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
