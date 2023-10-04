package com.nntk.restplus.returntype;

import com.nntk.restplus.AbsHttpFactory;
import com.nntk.restplus.BasicRespObserver;
import com.nntk.restplus.RespBodyHandleRule;
import com.nntk.restplus.util.HttpRespObserver;


import java.lang.reflect.Type;


public class Call<T> {

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    private int httpStatus;

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    private Throwable throwable;

    public BasicRespObserver getConfigObserver() {
        return configObserver;
    }

    public void setConfigObserver(BasicRespObserver configObserver) {
        this.configObserver = configObserver;
    }

    public RespBodyHandleRule getRespBodyHandleRule() {
        return respBodyHandleRule;
    }

    public void setRespBodyHandleRule(RespBodyHandleRule respBodyHandleRule) {
        this.respBodyHandleRule = respBodyHandleRule;
    }

    private BasicRespObserver configObserver;

    private RespBodyHandleRule respBodyHandleRule;

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    private Type returnType;

    private boolean isObserve = false;

    public AbsHttpFactory getHttpFactory() {
        return httpFactory;
    }

    public void setHttpFactory(AbsHttpFactory httpFactory) {
        this.httpFactory = httpFactory;
    }

    private AbsHttpFactory httpFactory;


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
        return httpFactory.parseObject(data, returnType);
    }

    public T executeForData() {
        if (!isObserve) {
            observe(configObserver);
        }
        String data = respBodyHandleRule.getData();
        return httpFactory.parseObject(data, returnType);
    }


}
