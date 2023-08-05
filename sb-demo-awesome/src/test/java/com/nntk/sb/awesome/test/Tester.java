package com.nntk.sb.awesome.test;


import com.nntk.sb.awesome.service.EhcacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Tester {


    @Autowired
    private EhcacheService ehcacheService;


    @Test
    public void testEhcache() {
        for (int i = 0; i < 5000; i++) {
            System.out.println(ehcacheService.getValue("1" + i));
        }

    }
}
