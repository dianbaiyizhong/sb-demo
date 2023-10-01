package com.nntk.sb.api.local;

import com.nntk.sb.rest.BasicCall;
import com.nntk.sb.rest.annotation.JalorRestProxy;
import com.nntk.sb.rest.annotation.LOCAL;

import java.util.List;

@JalorRestProxy(baseUrl = "https://api.github.com", respHandler = LocalRespBodyHandler.class)
public interface LocalApi {

    @LOCAL(url = "/users/{user}/repos")
    BasicCall<List<UserInfo>> readLocalFile();

}
