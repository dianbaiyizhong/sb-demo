package com.nntk.sb;

import cn.hutool.core.thread.ThreadUtil;
import com.nntk.sb.service.ITestService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestApp {


    @Autowired
    private ITestService testService;

    @Test
    @Order(1)
    public void test2() {
        testService.test();
        ThreadUtil.sleep(5000);
    }

    @Test
    @Order(2)
    public void test3() {
        testService.test();
        ThreadUtil.sleep(5000);
    }
}
