package com.nntk.sb.awesome;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
@EnableCaching
@EnableAsync
@MapperScan("com.nntk.sb.awesome.mapper") //添加mapper扫描
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
