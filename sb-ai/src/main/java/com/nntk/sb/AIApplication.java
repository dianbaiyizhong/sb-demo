package com.nntk.sb;


import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@MapperScans({
        @MapperScan("com.nntk.sb.mp.generate.mapper")
})
public class AIApplication {

    public static void main(String[] args) {
        SpringApplication.run(AIApplication.class, args);
    }


    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
