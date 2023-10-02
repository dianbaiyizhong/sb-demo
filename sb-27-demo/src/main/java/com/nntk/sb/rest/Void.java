package com.nntk.sb.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Void {
    private int httpStatus;
    private Throwable throwable;
    private BasicRespBodyHandler basicRespBodyHandler;

    public void observe(BasicRespObserver observer) {
        HttpRespObserver.observe(observer, throwable, httpStatus, basicRespBodyHandler);
    }

}
