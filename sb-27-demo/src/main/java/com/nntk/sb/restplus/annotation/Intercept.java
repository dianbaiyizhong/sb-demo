package com.nntk.sb.restplus.annotation;

import com.nntk.sb.restplus.intercept.ParamHandleIntercept;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Intercept {

    Class<? extends ParamHandleIntercept>[] classType() default {};

}