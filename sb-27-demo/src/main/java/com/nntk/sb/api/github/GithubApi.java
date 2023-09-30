package com.nntk.sb.api.github;

import com.nntk.sb.http.BasicCall;
import com.nntk.sb.http.annotation.GET;
import com.nntk.sb.http.annotation.JalorRestProxy;

import java.util.List;

@JalorRestProxy(baseUrl = "https://api.github.com", respHandler = GithubRespBodyHandler.class)
public interface GithubApi {
    @GET(url = "/users/dianbaiyizhong/repos")
    BasicCall<List<RepoInfo>> listRepos();

}
