package com.liu.spring.springeureka.securityconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 在地址里加上用户名密码，然后运行还是报错(client连接不上)，是因为新版本的security默认开启csrf了
 */
//因为 Spring Security 默认开启了所有 CSRF 攻击防御，需要禁用 /eureka 的防御。
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable(); //关闭csrf
        http.csrf().ignoringAntMatchers("/eureka/**");

//        super.configure(http);
//        http.authorizeRequests().anyRequest().authenticated().and().httpBasic(); //开启认证,这个是弹框验证账号密码
    }

}