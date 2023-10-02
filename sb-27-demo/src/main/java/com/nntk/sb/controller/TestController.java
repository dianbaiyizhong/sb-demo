package com.nntk.sb.controller;

import com.nntk.sb.api.github.GithubApi;
import com.nntk.sb.api.github.GithubPostBodyEntity;
import com.nntk.sb.api.github.GithubRepoInfo;
import com.nntk.sb.api.github.TestApi;
import com.nntk.sb.api.local.LocalApi;
import com.nntk.sb.api.local.LocalResultObserver;
import com.nntk.sb.api.local.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class TestController {

    @Resource
    private LocalApi localApi;

    @Resource
    private GithubApi githubApi;

    @Resource
    private TestApi testApi;

    @PostMapping("login")
    public Object login(@RequestBody Map<String, String> param) {
        log.info("=====login:{}", param);
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", 0);
        ret.put("message", "success");
        return ret;
    }


    @GetMapping("/local")
    public Object local() {
        List<UserInfo> data = localApi.readLocalFile()
                .observe(new LocalResultObserver() {
                    @Override
                    public void complete() {
                        super.complete();
                        log.info("=======完成，我要发送消息");
                    }
                }).getResult();

        return "success";
    }

    @GetMapping("/github")
    public Object github() {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("sex", "男");
        List<GithubRepoInfo> data = githubApi.listRepos("dianbaiyizhong", 1, paramMap)
                .getResult();
        return data;
    }

    @GetMapping("/test")
    public Object test() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("sex", "男");
        GithubPostBodyEntity result = testApi.login(paramMap).getResult();
        return result;
    }


}
