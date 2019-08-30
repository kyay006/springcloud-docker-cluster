package com.liu.spring.springzuul;

import com.liu.spring.springzuul.zuulfilter.RequestLogFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
@ComponentScan(basePackages = "com.liu")
//@EnableRedisHttpSession(redisFlushMode = RedisFlushMode.IMMEDIATE) //指定了redisFlushMode为立即刷新，防止因为redis刷新缓慢，导致session信息无法获取
public class SpringZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringZuulApplication.class, args);
    }

    @Bean
    public RequestLogFilter requestLogFilter(){
        return new RequestLogFilter();
    }



    @Value("${hello}")
    String hello;

    @RequestMapping("getHello")
    public String getHello()
    {
        System.out.println(hello);
        return hello;
    }



}
