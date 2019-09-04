package com.liu.spring.service.log;


//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient(value = "spring-order")
public interface LogFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/getHe")
    public String getHe();


}
