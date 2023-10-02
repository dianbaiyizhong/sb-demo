package com.nntk.sb.restplus.returntype;

import com.nntk.sb.restplus.BasicRespObserver;
import com.nntk.sb.restplus.RespBodyHandleRule;
import com.nntk.sb.restplus.util.HttpRespObserver;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Void {
    private int httpStatus;
    private Throwable throwable;
    private RespBodyHandleRule respBodyHandleRule;

    public void observe(BasicRespObserver observer) {
        HttpRespObserver.observe(observer, throwable, httpStatus, respBodyHandleRule);
    }

}
