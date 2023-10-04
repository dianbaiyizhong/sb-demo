package com.nntk.restplus.returntype;

import com.nntk.restplus.RespBodyHandleRule;



public class RestPlusVoid {
    private int httpStatus;

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public RespBodyHandleRule getRespBodyHandleRule() {
        return respBodyHandleRule;
    }

    public void setRespBodyHandleRule(RespBodyHandleRule respBodyHandleRule) {
        this.respBodyHandleRule = respBodyHandleRule;
    }

    private Throwable throwable;
    private RespBodyHandleRule respBodyHandleRule;
}
