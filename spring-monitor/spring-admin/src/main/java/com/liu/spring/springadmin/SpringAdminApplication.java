package com.liu.spring.springadmin;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableAdminServer
@EnableEurekaClient
@SpringBootApplication
public class SpringAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAdminApplication.class, args);
    }
}
