package com.liu.lovesound.springorder;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "spring-client")
public interface LogFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/getHe2")
    public String getHe2();


}
