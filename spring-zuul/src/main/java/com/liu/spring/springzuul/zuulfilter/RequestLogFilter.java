package com.liu.spring.springzuul.zuulfilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dalaoyang
 * @Description
 * @project springcloud_learn
 * @package com.dalaoyang.filter
 * @email yangyang@dalaoyang.cn
 * @date 2018/4/22
 */
public class RequestLogFilter extends ZuulFilter {

    Logger logger = LoggerFactory.getLogger(RequestLogFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    public boolean shouldFilter() {
//        return false;
        return true;
    }

    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();
        logger.info("请求路径："+httpServletRequest.getRequestURL().toString());


        return null;
    }

}