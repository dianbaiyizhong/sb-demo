package com.nntk.sb.restplus.strategy;

import com.nntk.sb.restplus.AbsHttpFactory;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class HttpExecuteContext {

    private String url;
    private Map<String, String> headerMap;
    private Map<String, Object> bodyMap;
    private AbsHttpFactory httpFactory;
}
