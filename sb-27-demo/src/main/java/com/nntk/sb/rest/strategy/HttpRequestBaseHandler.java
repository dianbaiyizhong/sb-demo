package com.nntk.sb.rest.strategy;

import com.nntk.sb.rest.HttpPlusResponse;
import org.aspectj.lang.ProceedingJoinPoint;

public interface HttpRequestBaseHandler {
    HttpPlusResponse execute(ProceedingJoinPoint method);

}
