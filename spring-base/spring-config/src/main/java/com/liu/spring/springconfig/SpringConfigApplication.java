package com.liu.spring.springconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableConfigServer
@EnableEurekaClient
@SpringBootApplication
public class SpringConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringConfigApplication.class, args);
    }


}
