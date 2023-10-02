package com.nntk.sb.restplus.annotation;

import com.nntk.sb.restplus.RespBodyHandleRule;
import com.nntk.sb.restplus.BasicRespObserver;
import com.nntk.sb.restplus.AbsHttpFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RestPlus {
    String baseUrl() default "";

    Class<? extends RespBodyHandleRule> respHandler() default RespBodyHandleRule.class;

    Class<? extends BasicRespObserver> observe() default BasicRespObserver.class;

    Class<? extends AbsHttpFactory> httpFactory() default AbsHttpFactory.class;

}
