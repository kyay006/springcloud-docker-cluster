package com.liu.spring.springzuul.zuulfilter;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class MyFallbackProvider implements FallbackProvider {

    //如果需要所有的路由服务都加熔断功能，需要在getRoute()方法上返回”*“的匹配符
    public String getRoute() {
        return "*";
    }

    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        System.out.println("route:"+route);
        System.out.println("exception:"+cause.getMessage());
        return new ClientHttpResponse() {
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            public int getRawStatusCode() throws IOException {
                return 200;
            }

            public String getStatusText() throws IOException {
                return "ok";
            }

            public void close() {

            }

            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("oooops!error,i'm the fallback.".getBytes());
            }

            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}