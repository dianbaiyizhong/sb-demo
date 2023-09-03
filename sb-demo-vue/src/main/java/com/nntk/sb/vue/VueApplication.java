package com.nntk.sb.vue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Slf4j
public class VueApplication {
    public static void main(String[] args) {
        SpringApplication.run(VueApplication.class, args);
    }
}
