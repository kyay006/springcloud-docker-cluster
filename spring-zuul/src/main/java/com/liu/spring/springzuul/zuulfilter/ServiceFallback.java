//package com.liu.spring.springzuul.zuulfilter;
//
//import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.client.ClientHttpResponse;
//import org.springframework.stereotype.Component;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.charset.Charset;
//
///**
// * 有时会出现这样的情况，比如本文的service实例down了，如下图这种情况
// * 那么在访问，就会无法转发，其实zuul也提供了失败回调的方法。
// * 新建一个ServiceFallback类实现ZuulFallbackProvider接口，需要重写2个方法。
// * 其中getRoute方法中可以指定为哪个微服务回退，*为所有服务。
// *
// * @author dalaoyang
// * @Description
// * @project springcloud_learn
// * @package com.dalaoyang.fallback
// * @email yangyang@dalaoyang.cn
// * @date 2018/4/22
// */
//@Component
//public class ServiceFallback implements ZuulFallbackProvider {
//
//    @Override
//    public String getRoute() {
//        return "*";
//    }
//
//    @Override
//    public ClientHttpResponse fallbackResponse() {
//        return new ClientHttpResponse() {
//            @Override
//            public HttpStatus getStatusCode() throws IOException {
//                return HttpStatus.OK;
//            }
//
//            @Override
//            public int getRawStatusCode() throws IOException {
//                return this.getStatusCode().value();
//            }
//
//            @Override
//            public String getStatusText() throws IOException {
//                return this.getStatusCode().getReasonPhrase();
//            }
//
//            @Override
//            public void close() {
//
//            }
//
//            @Override
//            public InputStream getBody() throws IOException {
//                return new ByteArrayInputStream("当前访问服务不可用！".getBytes());
//            }
//
//            @Override
//            public HttpHeaders getHeaders() {
//                HttpHeaders httpHeaders = new HttpHeaders();
//                MediaType mediaType = new MediaType("application", "json",
//                        Charset.forName("UTF-8"));
//                httpHeaders.setContentType(mediaType);
//                return httpHeaders;
//            }
//        };
//    }
//}