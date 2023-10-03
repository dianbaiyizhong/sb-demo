package com.nntk.restplus.intercept;

import com.nntk.restplus.strategy.HttpExecuteContext;

public interface ParamHandleIntercept {

    public HttpExecuteContext handle(HttpExecuteContext context);

}
