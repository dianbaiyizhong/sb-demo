package com.nntk.restplus.annotation;

import com.nntk.restplus.AbsHttpFactory;
import com.nntk.restplus.BasicRespObserver;
import com.nntk.restplus.RespBodyHandleRule;
import org.springframework.context.annotation.Import;

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
