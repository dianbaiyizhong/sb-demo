package com.nntk.sb.service;

import com.nntk.sb.s3.S3Client;

public interface IS3Proxy {


    public void execute(S3Client s3Client);
}
