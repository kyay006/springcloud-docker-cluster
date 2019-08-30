//package com.liu.spring.springconfig.securityconfig;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * 在地址里加上用户名密码，然后运行还是报错(client连接不上)，是因为新版本的security默认开启csrf了
// */
//@EnableWebSecurity
//@Configuration
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable(); //关闭csrf
//        http.authorizeRequests().anyRequest().authenticated().and().httpBasic(); //开启认证
//
//
//        //允许所有用户访问"/"和"/home"
////        http.authorizeRequests()
////                .antMatchers("/home").permitAll()
////                //其他地址的访问均需验证权限
////                .anyRequest().authenticated()
////                .and()
////                .formLogin()
////                .loginPage("/login")  //指定登录页是"/login"
////                .defaultSuccessUrl("/list")  //登录成功后默认跳转到"list"
////                .permitAll()
////                .and()
////                .logout()
////                .logoutSuccessUrl("/home")  //退出登录后的默认url是"/home"
////                .permitAll();
//
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        //解决静态资源被拦截的问题
//        web.ignoring().antMatchers("/actuator/**");
//        // 忽略URL
////        web.ignoring().antMatchers("/**/*.js", "/lang/*.json", "/**/*.css", "/**/*.js", "/**/*.map", "/**/*.html","/**/*.png");
//    }
//}