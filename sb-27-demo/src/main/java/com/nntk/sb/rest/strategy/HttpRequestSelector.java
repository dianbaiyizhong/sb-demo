package com.nntk.sb.rest.strategy;

import com.nntk.sb.rest.annotation.GET;
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

    @PostConstruct
    public void postConstruct() {

        selectorMap.put(GET.class, getRequestHandler);

    }

    public HttpRequestBaseHandler select(Class<? extends Annotation> type) {
        return selectorMap.get(type);
    }

}
