package com.nntk.sb.restplus.intercept;

import com.nntk.sb.restplus.strategy.HttpExecuteContext;

public interface ParamHandleIntercept {

    public HttpExecuteContext handle(HttpExecuteContext context);

}
