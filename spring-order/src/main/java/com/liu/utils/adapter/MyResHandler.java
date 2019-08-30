package com.liu.utils.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 资源映射
 */
@Component
public class MyResHandler extends WebMvcConfigurerAdapter {

    @Value("${customConfig.image.resourceMappingPath}")
    String resourceMappingPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/MP_verify_Lo5ZxMzKsqiAdzP2.txt").addResourceLocations("classpath:/MP_verify_Lo5ZxMzKsqiAdzP2.txt");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/images/favicon.ico");
        registry.addResourceHandler("/resource/**").addResourceLocations(resourceMappingPath);
        super.addResourceHandlers(registry);
    }
}