package com.nntk.sb.api.my;

import com.nntk.sb.api.HutoolAbsHttpFactory;
import com.nntk.sb.restplus.annotation.RestPlus;

@RestPlus(
        baseUrl = "http://localhost:8080",
        httpFactory = HutoolAbsHttpFactory.class
)
public interface MyBaseApi {

}
