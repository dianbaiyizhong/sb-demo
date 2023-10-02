package com.nntk.sb.restplus.strategy;

import com.nntk.sb.restplus.HttpPlusResponse;
import org.aspectj.lang.ProceedingJoinPoint;

public interface HttpRequestBaseHandler {
    HttpPlusResponse execute(ProceedingJoinPoint method);

}
