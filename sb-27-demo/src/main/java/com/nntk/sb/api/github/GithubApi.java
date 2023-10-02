package com.nntk.sb.api.github;

import com.nntk.sb.rest.BasicCall;
import com.nntk.sb.rest.annotation.*;
import java.util.List;
import java.util.Map;

@JalorRestProxy(baseUrl = "https://api.github.com", respHandler = GithubRespBodyHandler.class)
public interface GithubApi {
    @GET(url = "/users/{user}/repos")
    BasicCall<List<GithubRepoInfo>> listRepos(@Path("user") String userName, @QueryParam("id") int id, @QueryMap Map<String, Object> map);



}
