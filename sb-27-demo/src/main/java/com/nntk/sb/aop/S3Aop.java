package com.nntk.sb.aop;

import com.nntk.sb.s3.S3Client;
import com.nntk.sb.service.S3Service;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class S3Aop {

    @Pointcut("execution(* com.nntk.sb.service.S3Service.*(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        // 逻辑代码
        S3Service s3Service = (S3Service) joinPoint.getTarget();
        s3Service.setS3Client(new S3Client());

        System.out.println("---before");

    }

}
