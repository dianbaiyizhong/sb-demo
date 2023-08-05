package com.nntk.sb.awesome.controller;

import com.nntk.sb.awesome.service.EhcacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private EhcacheService ehcacheService;


    @RequestMapping("/ehcache")
    public String ehcache() {

        for (int i = 0; i < 5000; i++) {
            String value = ehcacheService.getValue("" + i);
            System.out.println(value);
        }
        return "success";

    }


}
