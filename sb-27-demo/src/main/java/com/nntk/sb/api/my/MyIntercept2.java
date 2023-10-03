package com.nntk.sb.api.my;

import com.nntk.sb.restplus.intercept.ParamHandleIntercept;
import com.nntk.sb.restplus.strategy.HttpExecuteContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyIntercept2 implements ParamHandleIntercept {
    @Override
    public HttpExecuteContext handle(HttpExecuteContext context) {

        log.info("=======MyIntercept2");

        return context;
    }
}
