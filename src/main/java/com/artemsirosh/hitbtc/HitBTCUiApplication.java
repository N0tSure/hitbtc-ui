package com.artemsirosh.hitbtc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class HitBTCUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HitBTCUiApplication.class, args);
    }

}
