package com.liu.springrbacmanage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@RestController
//@EnableEurekaClient
@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.liu", "common", "framework", "project"})
@MapperScan("project.*.*.mapper")
public class SpringRbacManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRbacManageApplication.class, args);
    }
}
