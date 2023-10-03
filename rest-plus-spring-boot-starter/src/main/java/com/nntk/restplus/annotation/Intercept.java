package com.nntk.restplus.annotation;

import com.nntk.restplus.intercept.ParamHandleIntercept;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Intercept {

    Class<? extends ParamHandleIntercept>[] classType() default {};

}