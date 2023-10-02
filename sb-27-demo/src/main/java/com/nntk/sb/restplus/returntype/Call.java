package com.nntk.sb.restplus.returntype;

import com.nntk.sb.restplus.AbsHttpFactory;
import com.nntk.sb.restplus.BasicRespObserver;
import com.nntk.sb.restplus.RespBodyHandleRule;
import com.nntk.sb.restplus.util.HttpRespObserver;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;

@Getter
@Setter
public class Call<T> {

    private int httpStatus;
    private Throwable throwable;

    private BasicRespObserver configObserver;

    private RespBodyHandleRule respBodyHandleRule;
    private Type retureType;

    private boolean isObserve = false;

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
        return httpFactory.parseObject(data, retureType);
    }

    public T executeForData() {
        if (!isObserve) {
            observe(configObserver);
        }
        String data = respBodyHandleRule.getData();
        return httpFactory.parseObject(data, retureType);
    }


}
