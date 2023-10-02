package com.nntk.sb.api.my;

import com.nntk.sb.api.RestTemplateHttpFactory;
import com.nntk.sb.restplus.annotation.RestPlus;

@RestPlus(
        baseUrl = "http://localhost:8080",
        httpFactory = RestTemplateHttpFactory.class
)
public interface MyBaseApi {

}
