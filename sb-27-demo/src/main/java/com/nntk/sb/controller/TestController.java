package com.nntk.sb.controller;

import com.nntk.sb.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private ITestService testService;


    @RequestMapping("/test")
    public String test(){

        testService.test();
        return "success";
    }

}
