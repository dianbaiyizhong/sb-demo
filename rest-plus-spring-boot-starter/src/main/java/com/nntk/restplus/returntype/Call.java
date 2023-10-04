package com.nntk.restplus.returntype;

import com.nntk.restplus.abs.AbsHttpFactory;
import com.nntk.restplus.abs.AbsBasicRespObserver;
import com.nntk.restplus.abs.AbsBodyHandleRule;
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

    public AbsBasicRespObserver getConfigObserver() {
        return configObserver;
    }

    public void setConfigObserver(AbsBasicRespObserver configObserver) {
        this.configObserver = configObserver;
    }

    public AbsBodyHandleRule getRespBodyHandleRule() {
        return absBodyHandleRule;
    }

    public void setRespBodyHandleRule(AbsBodyHandleRule absBodyHandleRule) {
        this.absBodyHandleRule = absBodyHandleRule;
    }

    private AbsBasicRespObserver configObserver;

    private AbsBodyHandleRule absBodyHandleRule;

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


    public Call<T> observe(AbsBasicRespObserver observer) {
        isObserve = true;
        HttpRespObserver.observe(observer, throwable, httpStatus, absBodyHandleRule);
        return this;
    }

    public T executeForResult() {
        if (!isObserve) {
            observe(configObserver);
        }
        String data = absBodyHandleRule.getHttpBody();
        return httpFactory.parseObject(data, returnType);
    }

    public T executeForData() {
        if (!isObserve) {
            observe(configObserver);
        }
        String data = absBodyHandleRule.getData();
        return httpFactory.parseObject(data, returnType);
    }


}
