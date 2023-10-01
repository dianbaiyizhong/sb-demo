package com.nntk.sb.rest.strategy;

import cn.hutool.http.HttpResponse;
import org.aspectj.lang.ProceedingJoinPoint;

public interface HttpRequestBaseHandler {
    HttpResponse execute(ProceedingJoinPoint method);

}
