package com.nntk.sb.restplus.annotation;

import com.nntk.sb.restplus.RespBodyHandleRule;
import com.nntk.sb.restplus.BasicRespObserver;
import com.nntk.sb.restplus.AbsHttpFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestPlus {
    String baseUrl() default "";

    Class<? extends RespBodyHandleRule> respHandler() default RespBodyHandleRule.class;

    Class<? extends BasicRespObserver> observe() default BasicRespObserver.class;

    Class<? extends AbsHttpFactory> httpFactory();

}
