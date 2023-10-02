package com.nntk.sb.rest.annotation;

import com.nntk.sb.rest.BasicRespBodyHandler;
import com.nntk.sb.rest.BasicRespObserver;
import com.nntk.sb.rest.HttpFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestPlus {
    String baseUrl() default "";

    Class<? extends BasicRespBodyHandler> respHandler() default BasicRespBodyHandler.class;

    Class<? extends BasicRespObserver> observe() default BasicRespObserver.class;

    Class<? extends HttpFactory> httpFactory();

}
