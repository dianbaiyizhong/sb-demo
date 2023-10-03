package com.nntk.sb.api.my;

import com.nntk.sb.api.DefaultResultObserver;
import com.nntk.sb.api.RestTemplateHttpFactory;
import com.nntk.restplus.annotation.RestPlus;

@RestPlus(
        httpFactory = RestTemplateHttpFactory.class,
        observe = DefaultResultObserver.class
)
public interface MyBaseApi {

}
