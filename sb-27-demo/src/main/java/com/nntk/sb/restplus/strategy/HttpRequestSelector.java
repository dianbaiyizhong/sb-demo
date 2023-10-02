package com.nntk.sb.restplus.strategy;

import com.nntk.sb.restplus.annotation.GET;
import com.nntk.sb.restplus.annotation.POST;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

@Component
public class HttpRequestSelector {

    private Map<Class<? extends Annotation>, HttpRequestBaseHandler> selectorMap = new HashMap<>();

    @Resource
    private GetRequestHandler getRequestHandler;

    @Resource
    private PostRequestHandler postRequestHandler;

    @PostConstruct
    public void postConstruct() {

        selectorMap.put(GET.class, getRequestHandler);
        selectorMap.put(POST.class, postRequestHandler);

    }

    public HttpRequestBaseHandler select(Class<? extends Annotation> type) {
        return selectorMap.get(type);
    }

}
