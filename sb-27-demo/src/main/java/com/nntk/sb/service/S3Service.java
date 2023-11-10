package com.nntk.sb.service;

import org.springframework.stereotype.Component;

@Component
public class S3Service {


    public void upload() {

        S3Proxy.execute(s3Client -> {

            System.out.println("S3Service---upload");

        });
    }

}
