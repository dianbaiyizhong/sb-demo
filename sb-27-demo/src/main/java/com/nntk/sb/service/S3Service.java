package com.nntk.sb.service;

import com.nntk.sb.s3.S3Client;
import org.springframework.stereotype.Component;

@Component
public class S3Service {

    public void setS3Client(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public S3Client getS3Client() {
        return s3Client;
    }

    private S3Client s3Client;

    public void upload() {
        System.out.println("=====" + s3Client);
        System.out.println("S3Service---upload");
    }

}
