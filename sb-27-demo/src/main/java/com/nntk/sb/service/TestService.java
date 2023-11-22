package com.nntk.sb.service;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestService implements ITestService {
    @Override
    public void test() {
        log.info("=======================test");
    }


    @Override
    @Async("myThreadPool")
    public void testAsync() {
        log.info("=======================testAsync");
        ThreadUtil.sleep(5000);
    }
}
