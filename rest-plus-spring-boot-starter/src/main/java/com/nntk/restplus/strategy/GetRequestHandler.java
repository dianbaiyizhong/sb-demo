package com.nntk.restplus.strategy;

import com.nntk.restplus.AbsHttpFactory;
import com.nntk.restplus.HttpPlusResponse;
import org.springframework.stereotype.Component;

@Component
public class GetRequestHandler extends HttpRequestBaseHandler {


    @Override
    public HttpPlusResponse executeHttp(HttpExecuteContext context) {
        AbsHttpFactory httpFactory = context.getHttpFactory();
        String url = context.getUrl();
        return httpFactory.get(url, context.getHeaderMap());
    }
}
