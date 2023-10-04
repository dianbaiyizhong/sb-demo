package com.nntk.restplus.strategy;

import com.nntk.restplus.abs.AbsHttpFactory;

import java.util.Map;

public class HttpExecuteContext {

    private String url;
    private Map<String, String> headerMap;
    private Map<String, Object> bodyMap;
    private AbsHttpFactory httpFactory;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public Map<String, Object> getBodyMap() {
        return bodyMap;
    }

    public void setBodyMap(Map<String, Object> bodyMap) {
        this.bodyMap = bodyMap;
    }

    public AbsHttpFactory getHttpFactory() {
        return httpFactory;
    }

    public void setHttpFactory(AbsHttpFactory httpFactory) {
        this.httpFactory = httpFactory;
    }


}
