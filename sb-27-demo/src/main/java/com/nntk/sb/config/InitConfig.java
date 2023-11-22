package com.nntk.sb.config;

import cn.hutool.core.thread.ThreadUtil;
import com.nntk.sb.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class InitConfig {


    @Autowired
    private ITestService testService;


    @PostConstruct
    public void initTask() {

        System.out.println("======initTask");

        testService.testAsync();


    }
}
