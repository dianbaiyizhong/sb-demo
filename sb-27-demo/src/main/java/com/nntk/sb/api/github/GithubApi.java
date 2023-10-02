package com.nntk.sb.api.github;

import com.nntk.sb.api.DefaultResultObserver;
import com.nntk.sb.api.HutoolHttpFactory;
import com.nntk.sb.rest.Call;
import com.nntk.sb.rest.annotation.*;

import java.util.List;
import java.util.Map;

@RestPlus(
        baseUrl = "https://api.github.com",
        respHandler = GithubRespBodyHandler.class,
        observe = DefaultResultObserver.class,
        httpFactory = HutoolHttpFactory.class
)
public interface GithubApi {
    @GET(url = "/users/{user}/repos")
    Call<List<GithubRepoInfo>> listRepos(@Path("user") String userName, @QueryParam("id") int id, @QueryMap Map<String, Object> map);

}
