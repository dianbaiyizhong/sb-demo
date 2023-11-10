package com.nntk.sb.service;

import com.nntk.sb.s3.S3Client;

public class S3Proxy {


    public static void execute(IS3Proxy is3Proxy) {
        S3Client s3Client = new S3Client();
        s3Client.init();
        is3Proxy.execute(s3Client);
        s3Client.close();
    }


}
