package com.nntk.sb.controller;

import com.nntk.sb.api.github.GithubApi;
import com.nntk.sb.api.github.GithubResultObserver;
import com.nntk.sb.api.github.RepoInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class TestController {

    @Resource
    private GithubApi githubApi;


    @GetMapping("/test")
    public Object test() {
        List<RepoInfo> data = githubApi.listRepos()
                .observe(new GithubResultObserver() {
                    @Override
                    public void complete() {
                        super.complete();
                        log.info("=======完成，我要发送消息");
                    }
                }).getData();

        return "success";
    }
}
