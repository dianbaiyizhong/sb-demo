package com.nntk.sb.restplus.strategy;

import com.nntk.sb.restplus.AbsHttpFactory;
import com.nntk.sb.restplus.HttpPlusResponse;
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
