package com.nntk.sb.controller;

import com.nntk.sb.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TestController {

    @Autowired
    private S3Service s3Service;


    @RequestMapping("/s3")
    public String s3(){

        s3Service.upload();
        return "s3";
    }

}
